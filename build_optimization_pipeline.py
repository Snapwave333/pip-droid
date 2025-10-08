#!/usr/bin/env python3
"""
COMPREHENSIVE ANDROID BUILD OPTIMIZATION PIPELINE
Pip-Boy Application - Build Automation Script
Version: 5.0 - Wear OS Extension
"""

import asyncio
import json
import os
import subprocess
import sys
import time
from dataclasses import dataclass
from datetime import datetime
from pathlib import Path
from typing import Dict, List, Optional
from enum import Enum

class BuildStage(Enum):
    VALIDATION = "validation"
    DEPENDENCY_CHECK = "dependency_check"
    STATIC_ANALYSIS = "static_analysis"
    COMPILATION = "compilation"
    TESTING = "testing"
    PACKAGING = "packaging"
    DEPLOYMENT = "deployment"

class BuildType(Enum):
    DEBUG = "debug"
    RELEASE = "release"
    BENCHMARK = "benchmark"

@dataclass
class BuildMetrics:
    stage_times: Dict[str, float]
    total_time: float = 0.0
    artifact_count: int = 0
    test_results: Dict[str, int] = None

    def __post_init__(self):
        if self.test_results is None:
            self.test_results = {}

@dataclass
class BuildConfig:
    project_root: str
    build_type: BuildType
    enable_wear_os: bool = True
    enable_dynamic_features: bool = True
    run_tests: bool = True
    enable_proguard: bool = True

class BuildOrchestrator:
    def __init__(self, config: BuildConfig):
        self.config = config
        self.metrics = BuildMetrics({})
        self.start_time = time.time()

    async def execute_pipeline(self) -> bool:
        print("🚀 PIP-BOY BUILD OPTIMIZATION PIPELINE")
        print("=" * 50)

        try:
            # Validation Stage
            await self._execute_stage(BuildStage.VALIDATION, self._validate_project)

            # Dependency Check
            await self._execute_stage(BuildStage.DEPENDENCY_CHECK, self._check_dependencies)

            # Static Analysis
            await self._execute_stage(BuildStage.STATIC_ANALYSIS, self._run_static_analysis)

            # Compilation
            await self._execute_stage(BuildStage.COMPILATION, self._compile_project)

            # Testing
            if self.config.run_tests:
                await self._execute_stage(BuildStage.TESTING, self._run_tests)

            # Packaging
            await self._execute_stage(BuildStage.PACKAGING, self._package_artifacts)

            return self._generate_report()

        except Exception as e:
            print(f"❌ BUILD FAILED: {e}")
            return False

    async def _execute_stage(self, stage: BuildStage, stage_function) -> None:
        print(f"\n📋 STAGE: {stage.value.upper()}")
        print("-" * 30)

        stage_start = time.time()
        try:
            await stage_function()
            stage_time = time.time() - stage_start
            self.metrics.stage_times[stage.value] = stage_time
            print(f"✅ {stage.value} completed in {stage_time:.2f}s")
        except Exception as e:
            print(f"❌ {stage.value} failed: {e}")
            raise

    async def _validate_project(self) -> None:
        """Validate project structure and configuration"""
        print("🔍 Validating project structure...")

        # Check for required files
        required_files = [
            "build.gradle",
            "gradle.properties",
            "src/main/AndroidManifest.xml"
        ]

        for file in required_files:
            if not os.path.exists(file):
                raise FileNotFoundError(f"Required file missing: {file}")

        # Validate module structure
        modules = [":app", ":domain", ":data", ":feature-status"]
        if self.config.enable_wear_os:
            modules.append(":wear")
        if self.config.enable_dynamic_features:
            modules.append(":dynamic-feature-inventory")

        for module in modules:
            module_path = module.replace(":", "src/")
            if not os.path.exists(module_path):
                raise FileNotFoundError(f"Module directory missing: {module_path}")

        print("✅ Project validation completed")

    async def _check_dependencies(self) -> None:
        """Check and resolve dependencies"""
        print("📦 Checking dependencies...")

        # Run Gradle dependency check
        await self._run_gradle_task("dependencies", "--refresh-dependencies")

        # Verify Wear OS dependencies if enabled
        if self.config.enable_wear_os:
            await self._run_gradle_task(":wear:dependencies")

        # Verify dynamic feature dependencies if enabled
        if self.config.enable_dynamic_features:
            await self._run_gradle_task(":dynamic-feature-inventory:dependencies")

        print("✅ Dependency check completed")

    async def _run_static_analysis(self) -> None:
        """Run static analysis tools"""
        print("🔍 Running static analysis...")

        # Check for Android Lint
        await self._run_gradle_task("lint", "lintDebug")

        # Check for Detekt (Kotlin static analysis)
        if os.path.exists("detekt.yml"):
            await self._run_command("./gradlew", "detekt")

        # Check for SpotBugs
        if os.path.exists("spotbugs.gradle"):
            await self._run_gradle_task("spotbugsDebug")

        print("✅ Static analysis completed")

    async def _compile_project(self) -> None:
        """Compile the project"""
        print("🔨 Compiling project...")

        tasks = ["compileDebugKotlin", "compileDebugJavaWithJavac"]

        if self.config.enable_wear_os:
            tasks.extend([
                ":wear:compileDebugKotlin",
                ":wear:compileDebugJavaWithJavac"
            ])

        if self.config.enable_dynamic_features:
            tasks.extend([
                ":dynamic-feature-inventory:compileDebugKotlin",
                ":dynamic-feature-inventory:compileDebugJavaWithJavac"
            ])

        for task in tasks:
            await self._run_gradle_task(task)

        print("✅ Compilation completed")

    async def _run_tests(self) -> None:
        """Run all tests"""
        print("🧪 Running tests...")

        # Unit tests
        await self._run_gradle_task("testDebugUnitTest")

        # Instrumentation tests
        await self._run_gradle_task("connectedDebugAndroidTest")

        # Wear OS tests if enabled
        if self.config.enable_wear_os:
            await self._run_gradle_task(":wear:connectedDebugAndroidTest")

        print("✅ Testing completed")

    async def _package_artifacts(self) -> None:
        """Package build artifacts"""
        print("📦 Packaging artifacts...")

        # Build base APK
        build_task = "assembleDebug"
        if self.config.build_type == BuildType.RELEASE:
            build_task = "assembleRelease"

        await self._run_gradle_task(build_task)

        # Build Wear OS APK if enabled
        if self.config.enable_wear_os:
            wear_task = ":wear:assembleDebug"
            if self.config.build_type == BuildType.RELEASE:
                wear_task = ":wear:assembleRelease"
            await self._run_gradle_task(wear_task)

        # Build dynamic features if enabled
        if self.config.enable_dynamic_features:
            df_task = ":dynamic-feature-inventory:assembleDebug"
            if self.config.build_type == BuildType.RELEASE:
                df_task = ":dynamic-feature-inventory:assembleRelease"
            await self._run_gradle_task(df_task)

        # Create universal APK if requested
        if self.config.create_universal_apk:
            await self._run_gradle_task("bundleDebug")

        print("✅ Packaging completed")

    async def _prepare_deployment(self) -> None:
        """Prepare for deployment"""
        print("🚀 Preparing deployment...")

        # Generate build reports
        await self._run_gradle_task("androidDependencies")
        await self._run_gradle_task("dependencies")

        # Verify APKs
        await self._verify_artifacts()

        print("✅ Deployment preparation completed")

    async def _run_gradle_task(self, *tasks: str) -> None:
        """Run Gradle task(s)"""
        cmd = ["./gradlew"] + list(tasks)

        try:
            result = subprocess.run(
                cmd,
                cwd=self.config.project_root,
                capture_output=True,
                text=True,
                timeout=300
            )

            if result.returncode != 0:
                print(f"Gradle task failed: {' '.join(tasks)}")
                print(f"Error: {result.stderr}")
                raise RuntimeError(f"Gradle task failed: {' '.join(tasks)}")

        except subprocess.TimeoutExpired:
            raise RuntimeError(f"Gradle task timed out: {' '.join(tasks)}")
        except FileNotFoundError:
            raise RuntimeError("Gradle wrapper not found. Please ensure ./gradlew exists")

    async def _run_command(self, *cmd: str) -> None:
        """Run arbitrary command"""
        try:
            result = subprocess.run(
                cmd,
                cwd=self.config.project_root,
                capture_output=True,
                text=True,
                timeout=60
            )

            if result.returncode != 0:
                print(f"Command failed: {' '.join(cmd)}")
                print(f"Error: {result.stderr}")

        except subprocess.TimeoutExpired:
            print(f"Command timed out: {' '.join(cmd)}")

    async def _verify_artifacts(self) -> None:
        """Verify generated artifacts"""
        build_dir = Path("build/outputs/apk")

        if build_dir.exists():
            apks = list(build_dir.rglob("*.apk"))
            bundles = list(build_dir.rglob("*.aab"))

            self.metrics.artifact_count = len(apks) + len(bundles)

            print(f"📊 Generated {len(apks)} APK(s) and {len(bundles)} bundle(s)")

            # Log artifact sizes
            for apk in apks:
                size = apk.stat().st_size
                print(f"   📱 {apk.name}: {size / 1024 / 1024:.1f}MB")

            for bundle in bundles:
                size = bundle.stat().st_size
                print(f"   📦 {bundle.name}: {size / 1024 / 1024:.1f}MB")

    def _generate_report(self) -> bool:
        """Generate build report"""
        self.metrics.total_time = time.time() - self.start_time

        print("\n📊 BUILD REPORT")
        print("=" * 30)
        print(f"Total time: {self.metrics.total_time:.2f}s")
        print(f"Artifacts generated: {self.metrics.artifact_count}")

        print("\nStage breakdown:")
        for stage, duration in self.metrics.stage_times.items():
            print(f"  {stage}: {duration:.2f}s")

        # Save detailed report
        report = {
            "timestamp": datetime.now().isoformat(),
            "build_type": self.config.build_type.value,
            "total_time": self.metrics.total_time,
            "stage_times": self.metrics.stage_times,
            "artifacts": self.metrics.artifact_count,
            "wear_os_enabled": self.config.enable_wear_os,
            "dynamic_features_enabled": self.config.enable_dynamic_features
        }

        with open("build_report.json", "w") as f:
            json.dump(report, f, indent=2)

        print("✅ Build completed successfully!")
        return True

async def main():
    """Main entry point"""
    parser = argparse.ArgumentParser(description="Pip-Boy Build Optimization Pipeline")
    parser.add_argument("--type", choices=["debug", "release", "benchmark"],
                       default="debug", help="Build type")
    parser.add_argument("--no-wear", action="store_true", help="Disable Wear OS build")
    parser.add_argument("--no-dynamic", action="store_true", help="Disable dynamic features")
    parser.add_argument("--no-tests", action="store_true", help="Skip tests")
    parser.add_argument("--project-root", default=".", help="Project root directory")

    args = parser.parse_args()

    config = BuildConfig(
        project_root=args.project_root,
        build_type=BuildType(args.type),
        enable_wear_os=not args.no_wear,
        enable_dynamic_features=not args.no_dynamic,
        run_tests=not args.no_tests
    )

    orchestrator = BuildOrchestrator(config)
    success = await orchestrator.execute_pipeline()

    sys.exit(0 if success else 1)

if __name__ == "__main__":
    asyncio.run(main())

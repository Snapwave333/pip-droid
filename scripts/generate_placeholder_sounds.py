#!/usr/bin/env python3
"""
Generate placeholder sound effects for Pip-Droid
These are simple sine wave beeps that can be replaced with ElevenLabs-generated sounds later.
"""

import numpy as np
import wave
import os
import struct

def generate_sine_wave(frequency, duration, sample_rate=44100, amplitude=0.5):
    """Generate a sine wave audio signal"""
    t = np.linspace(0, duration, int(sample_rate * duration), False)
    wave_data = amplitude * np.sin(2 * np.pi * frequency * t)
    return wave_data

def apply_envelope(wave_data, attack=0.01, release=0.05, sample_rate=44100):
    """Apply attack and release envelope to prevent clicks"""
    n_samples = len(wave_data)
    envelope = np.ones(n_samples)
    
    # Attack
    attack_samples = int(attack * sample_rate)
    if attack_samples > 0:
        envelope[:attack_samples] = np.linspace(0, 1, attack_samples)
    
    # Release
    release_samples = int(release * sample_rate)
    if release_samples > 0:
        envelope[-release_samples:] = np.linspace(1, 0, release_samples)
    
    return wave_data * envelope

def save_wav(filename, wave_data, sample_rate=44100):
    """Save wave data as WAV file"""
    # Convert to 16-bit PCM
    wave_data = np.int16(wave_data * 32767)
    
    with wave.open(filename, 'w') as wav_file:
        wav_file.setnchannels(1)  # Mono
        wav_file.setsampwidth(2)  # 16-bit
        wav_file.setframerate(sample_rate)
        wav_file.writeframes(wave_data.tobytes())
    
    print(f"Created: {filename}")

def generate_button_click(output_dir):
    """Short, crisp click"""
    frequency = 800
    duration = 0.05
    wave_data = generate_sine_wave(frequency, duration)
    wave_data = apply_envelope(wave_data, attack=0.001, release=0.02)
    save_wav(os.path.join(output_dir, "button_click.wav"), wave_data)

def generate_button_hover(output_dir):
    """Subtle hover feedback"""
    frequency = 600
    duration = 0.03
    wave_data = generate_sine_wave(frequency, duration, amplitude=0.3)
    wave_data = apply_envelope(wave_data, attack=0.005, release=0.015)
    save_wav(os.path.join(output_dir, "button_hover.wav"), wave_data)

def generate_tab_switch(output_dir):
    """Quick beep for tab switching"""
    frequency = 700
    duration = 0.15
    wave_data = generate_sine_wave(frequency, duration)
    wave_data = apply_envelope(wave_data, attack=0.01, release=0.05)
    save_wav(os.path.join(output_dir, "tab_switch.wav"), wave_data)

def generate_page_turn(output_dir):
    """Swoosh-like page turn"""
    # Falling frequency sweep
    duration = 0.3
    sample_rate = 44100
    t = np.linspace(0, duration, int(sample_rate * duration), False)
    freq_sweep = 1000 * np.exp(-3 * t)  # Exponential decay from 1000Hz
    wave_data = 0.5 * np.sin(2 * np.pi * freq_sweep * t)
    wave_data = apply_envelope(wave_data, attack=0.01, release=0.1)
    save_wav(os.path.join(output_dir, "page_turn.wav"), wave_data)

def generate_terminal_type(output_dir):
    """Clicky keyboard sound"""
    frequency = 900
    duration = 0.04
    wave_data = generate_sine_wave(frequency, duration)
    wave_data = apply_envelope(wave_data, attack=0.001, release=0.02)
    save_wav(os.path.join(output_dir, "terminal_type.wav"), wave_data)

def generate_terminal_enter(output_dir):
    """Satisfying confirmation"""
    frequencies = [600, 800]  # Two-tone
    duration = 0.2
    wave_data = np.zeros(int(44100 * duration))
    for i, freq in enumerate(frequencies):
        start = int(i * 0.05 * 44100)
        tone = generate_sine_wave(freq, 0.15, amplitude=0.4)
        tone = apply_envelope(tone, attack=0.01, release=0.05)
        wave_data[start:start+len(tone)] += tone
    save_wav(os.path.join(output_dir, "terminal_enter.wav"), wave_data)

def generate_terminal_error(output_dir):
    """Error warning beep"""
    frequencies = [500, 400]  # Descending two-tone
    duration = 0.4
    wave_data = np.zeros(int(44100 * duration))
    for i, freq in enumerate(frequencies):
        start = int(i * 0.15 * 44100)
        tone = generate_sine_wave(freq, 0.15, amplitude=0.5)
        tone = apply_envelope(tone, attack=0.01, release=0.05)
        wave_data[start:start+len(tone)] += tone
    save_wav(os.path.join(output_dir, "terminal_error.wav"), wave_data)

def generate_terminal_boot(output_dir):
    """Boot sequence"""
    duration = 2.0
    sample_rate = 44100
    wave_data = np.zeros(int(sample_rate * duration))
    
    # Rising frequency sweep
    t = np.linspace(0, 1.5, int(sample_rate * 1.5), False)
    freq_sweep = 200 + 400 * t  # 200Hz to 600Hz
    boot_sound = 0.4 * np.sin(2 * np.pi * freq_sweep * t)
    wave_data[:len(boot_sound)] = boot_sound
    
    # Final tone
    final_tone = generate_sine_wave(600, 0.5, amplitude=0.5)
    wave_data[-len(final_tone):] += final_tone
    
    wave_data = apply_envelope(wave_data, attack=0.05, release=0.1)
    save_wav(os.path.join(output_dir, "terminal_boot.wav"), wave_data)

def generate_quest_complete(output_dir):
    """Triumphant fanfare"""
    frequencies = [523, 659, 784]  # C-E-G chord
    duration = 1.5
    wave_data = np.zeros(int(44100 * duration))
    
    for i, freq in enumerate(frequencies):
        start = int(i * 0.15 * 44100)
        tone = generate_sine_wave(freq, 1.0, amplitude=0.3)
        tone = apply_envelope(tone, attack=0.02, release=0.3)
        wave_data[start:start+len(tone)] += tone
    
    save_wav(os.path.join(output_dir, "quest_complete.wav"), wave_data)

def generate_level_up(output_dir):
    """Power-up sound"""
    duration = 2.0
    sample_rate = 44100
    t = np.linspace(0, duration, int(sample_rate * duration), False)
    
    # Rising frequency with harmonics
    freq_base = 200 + 600 * (t / duration) ** 2  # Exponential rise
    wave_data = 0.4 * np.sin(2 * np.pi * freq_base * t)
    wave_data += 0.2 * np.sin(4 * np.pi * freq_base * t)  # Harmonic
    
    wave_data = apply_envelope(wave_data, attack=0.05, release=0.2)
    save_wav(os.path.join(output_dir, "level_up.wav"), wave_data)

def generate_achievement_unlock(output_dir):
    """Epic achievement sound"""
    frequencies = [523, 659, 784, 1047]  # Ascending arpeggio
    duration = 2.5
    wave_data = np.zeros(int(44100 * duration))
    
    for i, freq in enumerate(frequencies):
        start = int(i * 0.2 * 44100)
        tone = generate_sine_wave(freq, 0.8, amplitude=0.3)
        tone = apply_envelope(tone, attack=0.02, release=0.3)
        wave_data[start:start+len(tone)] += tone
    
    save_wav(os.path.join(output_dir, "achievement_unlock.wav"), wave_data)

def generate_error_beep(output_dir):
    """Short error beep"""
    frequencies = [600, 500]  # Descending
    duration = 0.5
    wave_data = np.zeros(int(44100 * duration))
    for i, freq in enumerate(frequencies):
        start = int(i * 0.15 * 44100)
        tone = generate_sine_wave(freq, 0.2, amplitude=0.5)
        tone = apply_envelope(tone, attack=0.01, release=0.05)
        wave_data[start:start+len(tone)] += tone
    save_wav(os.path.join(output_dir, "error_beep.wav"), wave_data)

def generate_success_beep(output_dir):
    """Short success beep"""
    frequencies = [500, 600]  # Ascending
    duration = 0.4
    wave_data = np.zeros(int(44100 * duration))
    for i, freq in enumerate(frequencies):
        start = int(i * 0.12 * 44100)
        tone = generate_sine_wave(freq, 0.2, amplitude=0.5)
        tone = apply_envelope(tone, attack=0.01, release=0.05)
        wave_data[start:start+len(tone)] += tone
    save_wav(os.path.join(output_dir, "success_beep.wav"), wave_data)

def generate_all_placeholders():
    """Generate all placeholder sound effects"""
    output_dir = os.path.join(os.path.dirname(__file__), "..", "src", "main", "res", "raw")
    os.makedirs(output_dir, exist_ok=True)
    
    print("Generating placeholder sound effects...")
    print(f"Output directory: {output_dir}")
    print()
    
    # Generate essential sounds
    generate_button_click(output_dir)
    generate_button_hover(output_dir)
    generate_tab_switch(output_dir)
    generate_page_turn(output_dir)
    generate_terminal_type(output_dir)
    generate_terminal_enter(output_dir)
    generate_terminal_error(output_dir)
    generate_terminal_boot(output_dir)
    generate_quest_complete(output_dir)
    generate_level_up(output_dir)
    generate_achievement_unlock(output_dir)
    generate_error_beep(output_dir)
    generate_success_beep(output_dir)
    
    # Create minimal placeholders for remaining sounds (to prevent missing resource errors)
    minimal_sounds = [
        "panel_open", "panel_close", "scroll", "back_button",
        "quest_accept", "objective_complete", "radio_static",
        "radio_on", "radio_off", "station_switch", "geiger_click",
        "vault_hum", "alert", "shutdown"
    ]
    
    for sound_name in minimal_sounds:
        wave_data = generate_sine_wave(600, 0.3, amplitude=0.4)
        wave_data = apply_envelope(wave_data, attack=0.01, release=0.05)
        save_wav(os.path.join(output_dir, f"{sound_name}.wav"), wave_data)
    
    print()
    print("✅ All placeholder sound effects generated!")
    print("📝 NOTE: Replace these with ElevenLabs-generated sounds for production quality")

if __name__ == "__main__":
    generate_all_placeholders()


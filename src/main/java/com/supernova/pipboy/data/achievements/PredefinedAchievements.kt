package com.supernova.pipboy.data.achievements

/**
 * All predefined achievements for Pip-Boy
 * Total: 30 achievements across 7 categories
 */
object PredefinedAchievements {
    
    val ALL_ACHIEVEMENTS = listOf(
        // ========== GENERAL (8 achievements) ==========
        Achievement(
            id = "vault_dweller",
            title = "Vault Dweller",
            description = "Complete the boot sequence for the first time",
            category = AchievementCategory.GENERAL,
            xpReward = 50,
            tier = AchievementTier.COMMON,
            unlockCondition = UnlockCondition.BootSequenceCompleted
        ),
        Achievement(
            id = "wasteland_wanderer",
            title = "Wasteland Wanderer",
            description = "Open Pip-Boy 100 times",
            category = AchievementCategory.GENERAL,
            xpReward = 100,
            tier = AchievementTier.COMMON,
            unlockCondition = UnlockCondition.OpenApp(100),
            maxProgress = 100
        ),
        Achievement(
            id = "dedicated_survivor",
            title = "Dedicated Survivor",
            description = "Use Pip-Boy for 7 consecutive days",
            category = AchievementCategory.GENERAL,
            xpReward = 200,
            tier = AchievementTier.UNCOMMON,
            unlockCondition = UnlockCondition.UseAppConsecutiveDays(7),
            maxProgress = 7
        ),
        Achievement(
            id = "wasteland_veteran",
            title = "Wasteland Veteran",
            description = "Use Pip-Boy for 30 total days",
            category = AchievementCategory.GENERAL,
            xpReward = 500,
            tier = AchievementTier.RARE,
            unlockCondition = UnlockCondition.UseAppDays(30),
            maxProgress = 30
        ),
        Achievement(
            id = "pip_boy_master",
            title = "Pip-Boy Master",
            description = "Open Pip-Boy 1000 times",
            category = AchievementCategory.GENERAL,
            xpReward = 1000,
            tier = AchievementTier.EPIC,
            unlockCondition = UnlockCondition.OpenApp(1000),
            maxProgress = 1000
        ),
        Achievement(
            id = "obsessed",
            title = "Obsessed",
            description = "Use Pip-Boy for 100 consecutive days",
            category = AchievementCategory.GENERAL,
            xpReward = 2000,
            tier = AchievementTier.LEGENDARY,
            unlockCondition = UnlockCondition.UseAppConsecutiveDays(100),
            maxProgress = 100
        ),
        Achievement(
            id = "early_bird",
            title = "Early Bird",
            description = "Open Pip-Boy before 6 AM",
            category = AchievementCategory.GENERAL,
            xpReward = 50,
            tier = AchievementTier.COMMON,
            unlockCondition = UnlockCondition.Custom("early_bird")
        ),
        Achievement(
            id = "night_owl",
            title = "Night Owl",
            description = "Open Pip-Boy after midnight",
            category = AchievementCategory.GENERAL,
            xpReward = 50,
            tier = AchievementTier.COMMON,
            unlockCondition = UnlockCondition.Custom("night_owl")
        ),
        
        // ========== TERMINAL (6 achievements) ==========
        Achievement(
            id = "terminal_novice",
            title = "Terminal Novice",
            description = "Execute 10 terminal commands",
            category = AchievementCategory.TERMINAL,
            xpReward = 100,
            tier = AchievementTier.COMMON,
            unlockCondition = UnlockCondition.ExecuteCommands(10),
            maxProgress = 10
        ),
        Achievement(
            id = "terminal_hacker",
            title = "Terminal Hacker",
            description = "Execute 100 terminal commands",
            category = AchievementCategory.TERMINAL,
            xpReward = 300,
            tier = AchievementTier.UNCOMMON,
            unlockCondition = UnlockCondition.ExecuteCommands(100),
            maxProgress = 100
        ),
        Achievement(
            id = "terminal_master",
            title = "Terminal Master",
            description = "Execute 500 terminal commands",
            category = AchievementCategory.TERMINAL,
            xpReward = 1000,
            tier = AchievementTier.RARE,
            unlockCondition = UnlockCondition.ExecuteCommands(500),
            maxProgress = 500
        ),
        Achievement(
            id = "help_seeker",
            title = "Help Seeker",
            description = "Use the 'help' command 5 times",
            category = AchievementCategory.TERMINAL,
            xpReward = 50,
            tier = AchievementTier.COMMON,
            unlockCondition = UnlockCondition.UseSpecificCommand("help", 5),
            maxProgress = 5
        ),
        Achievement(
            id = "easter_egg_hunter",
            title = "Easter Egg Hunter",
            description = "Discover a terminal easter egg",
            category = AchievementCategory.TERMINAL,
            xpReward = 200,
            tier = AchievementTier.UNCOMMON,
            unlockCondition = UnlockCondition.DiscoverEasterEgg("any")
        ),
        Achievement(
            id = "vault_secret",
            title = "Vault Secret",
            description = "Discover the hidden vault command",
            category = AchievementCategory.TERMINAL,
            xpReward = 500,
            tier = AchievementTier.RARE,
            unlockCondition = UnlockCondition.Custom("vault_secret")
        ),
        
        // ========== QUESTS (6 achievements) ==========
        Achievement(
            id = "quest_beginner",
            title = "Quest Beginner",
            description = "Complete your first quest",
            category = AchievementCategory.QUESTS,
            xpReward = 100,
            tier = AchievementTier.COMMON,
            unlockCondition = UnlockCondition.CompleteQuests(1),
            maxProgress = 1
        ),
        Achievement(
            id = "task_manager",
            title = "Task Manager",
            description = "Complete 10 quests",
            category = AchievementCategory.QUESTS,
            xpReward = 250,
            tier = AchievementTier.UNCOMMON,
            unlockCondition = UnlockCondition.CompleteQuests(10),
            maxProgress = 10
        ),
        Achievement(
            id = "quest_master",
            title = "Quest Master",
            description = "Complete 50 quests",
            category = AchievementCategory.QUESTS,
            xpReward = 750,
            tier = AchievementTier.RARE,
            unlockCondition = UnlockCondition.CompleteQuests(50),
            maxProgress = 50
        ),
        Achievement(
            id = "completionist",
            title = "Completionist",
            description = "Complete 200 quests",
            category = AchievementCategory.QUESTS,
            xpReward = 2000,
            tier = AchievementTier.LEGENDARY,
            unlockCondition = UnlockCondition.CompleteQuests(200),
            maxProgress = 200
        ),
        Achievement(
            id = "streak_master",
            title = "Streak Master",
            description = "Complete quests for 7 days in a row",
            category = AchievementCategory.QUESTS,
            xpReward = 500,
            tier = AchievementTier.RARE,
            unlockCondition = UnlockCondition.CompleteQuestStreak(7),
            maxProgress = 7
        ),
        Achievement(
            id = "branching_path",
            title = "Branching Path",
            description = "Complete a quest with a branching objective",
            category = AchievementCategory.QUESTS,
            xpReward = 150,
            tier = AchievementTier.UNCOMMON,
            unlockCondition = UnlockCondition.Custom("branching_quest")
        ),

        // ========== C.A.M.P. (5 achievements) ==========
        Achievement(
            id = "camp_builder",
            title = "C.A.M.P. Builder",
            description = "Enter build mode for the first time",
            category = AchievementCategory.EXPLORATION,
            xpReward = 250,
            tier = AchievementTier.COMMON,
            unlockCondition = UnlockCondition.Custom("build_mode_entered")
        ),

        Achievement(
            id = "workshop_tinkerer",
            title = "Workshop Tinkerer",
            description = "Customize your C.A.M.P. settings",
            category = AchievementCategory.EXPLORATION,
            xpReward = 500,
            tier = AchievementTier.COMMON,
            unlockCondition = UnlockCondition.Custom("workshop_activated")
        ),

        Achievement(
            id = "inventory_manager",
            title = "Inventory Manager",
            description = "Access the inventory wall",
            category = AchievementCategory.EXPLORATION,
            xpReward = 250,
            tier = AchievementTier.COMMON,
            unlockCondition = UnlockCondition.Custom("inventory_wall_used")
        ),

        Achievement(
            id = "radio_operator",
            title = "Radio Operator",
            description = "Activate the radio terminal",
            category = AchievementCategory.EXPLORATION,
            xpReward = 250,
            tier = AchievementTier.COMMON,
            unlockCondition = UnlockCondition.Custom("radio_terminal_used")
        ),

        Achievement(
            id = "cartographer",
            title = "Cartographer",
            description = "Access the map table",
            category = AchievementCategory.EXPLORATION,
            xpReward = 250,
            tier = AchievementTier.COMMON,
            unlockCondition = UnlockCondition.Custom("map_table_used")
        ),

        // ========== STATS (4 achievements) ==========
        Achievement(
            id = "level_5",
            title = "Growing Stronger",
            description = "Reach level 5",
            category = AchievementCategory.STATS,
            xpReward = 100,
            tier = AchievementTier.COMMON,
            unlockCondition = UnlockCondition.ReachTotalLevel(5),
            maxProgress = 5
        ),
        Achievement(
            id = "level_10",
            title = "Experienced Wanderer",
            description = "Reach level 10",
            category = AchievementCategory.STATS,
            xpReward = 300,
            tier = AchievementTier.UNCOMMON,
            unlockCondition = UnlockCondition.ReachTotalLevel(10),
            maxProgress = 10
        ),
        Achievement(
            id = "level_25",
            title = "Wasteland Legend",
            description = "Reach level 25",
            category = AchievementCategory.STATS,
            xpReward = 1000,
            tier = AchievementTier.RARE,
            unlockCondition = UnlockCondition.ReachTotalLevel(25),
            maxProgress = 25
        ),
        Achievement(
            id = "maxed_out",
            title = "Maxed Out",
            description = "Reach level 10 in all S.P.E.C.I.A.L. stats",
            category = AchievementCategory.STATS,
            xpReward = 5000,
            tier = AchievementTier.LEGENDARY,
            unlockCondition = UnlockCondition.MaxAllStats
        ),
        
        // ========== RADIO (3 achievements) ==========
        Achievement(
            id = "radio_listener",
            title = "Radio Listener",
            description = "Listen to 3 different radio stations",
            category = AchievementCategory.RADIO,
            xpReward = 100,
            tier = AchievementTier.COMMON,
            unlockCondition = UnlockCondition.ListenToStations(3),
            maxProgress = 3
        ),
        Achievement(
            id = "station_surfer",
            title = "Station Surfer",
            description = "Listen to 10 different radio stations",
            category = AchievementCategory.RADIO,
            xpReward = 250,
            tier = AchievementTier.UNCOMMON,
            unlockCondition = UnlockCondition.ListenToStations(10),
            maxProgress = 10
        ),
        Achievement(
            id = "radio_fanatic",
            title = "Radio Fanatic",
            description = "Listen to radio for 60 minutes total",
            category = AchievementCategory.RADIO,
            xpReward = 500,
            tier = AchievementTier.RARE,
            unlockCondition = UnlockCondition.ListenDuration(60),
            maxProgress = 60
        ),
        
        // ========== EXPLORATION (2 achievements) ==========
        Achievement(
            id = "explorer",
            title = "Explorer",
            description = "Visit all main tabs",
            category = AchievementCategory.EXPLORATION,
            xpReward = 100,
            tier = AchievementTier.COMMON,
            unlockCondition = UnlockCondition.Custom("visit_all_tabs")
        ),
        Achievement(
            id = "settings_tinkerer",
            title = "Settings Tinkerer",
            description = "Change 5 different settings",
            category = AchievementCategory.EXPLORATION,
            xpReward = 150,
            tier = AchievementTier.UNCOMMON,
            unlockCondition = UnlockCondition.Custom("change_settings"),
            maxProgress = 5
        ),
        
        // ========== SPECIAL (1 achievement) ==========
        Achievement(
            id = "platinum_wanderer",
            title = "Platinum Wanderer",
            description = "Unlock all other achievements",
            category = AchievementCategory.SPECIAL,
            xpReward = 10000,
            tier = AchievementTier.LEGENDARY,
            unlockCondition = UnlockCondition.AllAchievementsUnlocked
        )
    )
    
    /**
     * Get achievements by category
     */
    fun getByCategory(category: AchievementCategory): List<Achievement> {
        return ALL_ACHIEVEMENTS.filter { it.category == category }
    }
    
    /**
     * Get achievements by tier
     */
    fun getByTier(tier: AchievementTier): List<Achievement> {
        return ALL_ACHIEVEMENTS.filter { it.tier == tier }
    }
    
    /**
     * Get total possible XP
     */
    fun getTotalXP(): Int {
        return ALL_ACHIEVEMENTS.sumOf { it.xpReward }
    }
    
    /**
     * Statistics
     */
    object Stats {
        val totalAchievements = ALL_ACHIEVEMENTS.size // 35
        val commonCount = ALL_ACHIEVEMENTS.count { it.tier == AchievementTier.COMMON } // 17
        val uncommonCount = ALL_ACHIEVEMENTS.count { it.tier == AchievementTier.UNCOMMON } // 9
        val rareCount = ALL_ACHIEVEMENTS.count { it.tier == AchievementTier.RARE } // 6
        val epicCount = ALL_ACHIEVEMENTS.count { it.tier == AchievementTier.EPIC } // 1
        val legendaryCount = ALL_ACHIEVEMENTS.count { it.tier == AchievementTier.LEGENDARY } // 2
        val totalXP = getTotalXP() // 27,500+ XP
    }
}


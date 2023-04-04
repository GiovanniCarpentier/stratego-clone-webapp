"use strict";

const pawns =
    {
        "flag": {
            "rank": "F",
            "img": "stratego-flag.svg",
            "description": "If captured by an enemy you lose.\nProtect at all costs!!!"
        },
        "bomb": {
            "rank": "B",
            "img": "stratego-bomb.svg",
            "description": "Can only be removed by a miner.\nKills all atackers exept for miners."
        },
        "spy": {
            "rank": 1,
            "img": "stratego-spy.svg",
            "description": "Can only kill a marshal.\nKeep it safe!"
        },
        "infiltrator": {
            "rank": 1,
            "img": "stratego-infiltrator.svg",
            "description": "Can move 2 steps instead of 1 when attacking the enemy's field.\n"+
            "When attacking you need to guess what rank the opposing player has.\n"+
            "If successful you can kill it and you return to the place where you came from.\n"+
            "When you guess incorrectly you just return."
        },
        "scout": {
            "rank": 2,
            "img": "stratego-scout.svg",
            "description": "Rank 2, Can kill enemy's from rank 2 and below."
        },
        "miner": {
            "rank": 3,
            "img": "stratego-miner.svg",
            "description": "Rank 3, Can kill enemy's from rank 3 and below and disable bombs."
        },
        "sergeant": {
            "rank": 4,
            "img": "stratego-sergeant.svg",
            "description": "Rank 4, Can kill enemy's from rank 4 and below."
        },
        "lieutenant": {
            "rank": 5,
            "img": "stratego-lieutenant.svg",
            "description": "Rank 5, Can kill enemy's from rank 5 and below."
        },
        "captain": {
            "rank": 6,
            "img": "stratego-captain.svg",
            "description": "Rank 6, Can kill enemy's from rank 6 and below."
        },
        "major": {
            "rank": 7,
            "img": "stratego-major.svg",
            "description": "Rank 7, Can kill enemy's from rank 7 and below."
        },
        "colonel": {
            "rank": 8,
            "img": "stratego-colonel.svg",
            "description": "Rank 8, Can kill enemy's from rank 8 and below."
        },
        "general": {
            "rank": 9,
            "img": "stratego-general.svg",
            "description": "Rank 9, Can kill enemy's from rank 9 and below."
        },

        "marshal": {
            "rank": 10,
            "img": "stratego-marshal.svg",
            "description": "Rank 10, Can kill all enemy's except for bombs.\nCan only be killed by bombs, spy's and other marshal."
        }
    };







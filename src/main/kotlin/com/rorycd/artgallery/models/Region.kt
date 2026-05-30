package com.rorycd.artgallery.models

enum class AustralianState(val displayName: String) {
    NT("Northern Territory"),
    QLD("Queensland"),
    NSW("New South Wales"),
    VIC("Victoria"),
    SA("South Australia"),
    WA("Western Australia"),
    TAS("Tasmania"),
    ACT("Australian Capital Territory")
}

enum class Region(val displayName: String, val state: AustralianState?) {
    // NT
    ALICE_SPRINGS("Alice Springs and Central Desert", AustralianState.NT),
    AMPILATWATJA("Ampilatwatja", AustralianState.NT),
    ARNHEM_LAND("Arnhem Land", AustralianState.NT),
    BULGUL("Bulgul", AustralianState.NT),
    DARWIN("Darwin", AustralianState.NT),
    HAASTS_BLUFF("Haasts Bluff", AustralianState.NT),
    KATHERINE("Katherine", AustralianState.NT),
    KINTORE("Kintore", AustralianState.NT),
    LAJAMANU("Lajamanu", AustralianState.NT),
    MOUNT_ALLAN("Mount Allan", AustralianState.NT),
    MT_LIEBIG("Mt Liebig", AustralianState.NT),
    NYIRRIPI("Nyirripi", AustralianState.NT),
    PAPUNYA("Papunya", AustralianState.NT),
    UTOPIA("Utopia", AustralianState.NT),
    WILLOWRA("Willowra", AustralianState.NT),
    YUENDUMU("Yuendumu", AustralianState.NT),

    // QLD
    LOCKHART_RIVER("Lockhart River", AustralianState.QLD),
    SOUTH_WEST("South West", AustralianState.QLD),

    // SA
    APY_LANDS("APY Lands", AustralianState.SA),

    // WA
    IRRUNYTJU("Irrunytju", AustralianState.WA),
    KIMBERLEYS("Kimberleys", AustralianState.WA),
    KIWIRRKURRA("Kiwirrkurra", AustralianState.WA),
    TJUKURLA("Tjukurla", AustralianState.WA),
    WARAKURNA("Warakurna", AustralianState.WA),
    WARBURTON("Warburton", AustralianState.WA),

    // Other
    EAST_COAST("East Coast, QLD and NSW", null),
    INDEPENDENT("Independent", null),
    WESTERN_DESERT("Western Desert", null);

    override fun toString() = displayName
}
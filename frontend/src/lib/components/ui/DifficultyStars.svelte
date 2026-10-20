<script lang="ts">
    import { GAME_MODES, type GameMode } from '#lib/api/types.ts'

    let { mode }: { mode: GameMode } = $props()

    const STAR =
        '0,-9 2.12,-2.91 8.56,-2.78 3.42,1.11 5.29,7.28 0,3.6 -5.29,7.28 -3.42,1.11 -8.56,-2.78 -2.12,-2.91'
    const SPACING = 23

    const level = $derived(GAME_MODES.indexOf(mode) + 1)
</script>

<svg
    viewBox="0 0 {level * SPACING} 22"
    width={level * SPACING}
    height="22"
    role="img"
    aria-label="{mode}: moeilijkheid {level} van 3"
>
    <g
        fill="#F5B82E"
        stroke="#E8950C"
        stroke-width="0.75"
        stroke-linejoin="round"
    >
        {#each { length: level }, i (i)}
            <polygon
                points={STAR}
                transform="translate({11 + i * SPACING}, 11)"
            />
        {/each}
    </g>
</svg>

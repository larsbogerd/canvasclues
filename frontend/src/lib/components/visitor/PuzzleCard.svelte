<script lang="ts">
    import { resolve } from '$app/paths'
    import type { GameSummary } from '#lib/api/types.ts'
    import DifficultyStars from '#lib/components/ui/DifficultyStars.svelte'
    import { ART_SOURCES } from '#lib/sources.ts'

    let { game }: { game: GameSummary } = $props()
</script>

<a
    class="puzzle"
    href={resolve('/game/bezoeker/[gameId=gameId]', { gameId: game.gameId })}
>
    <span class="top">
        <span class="hint">{game.hint}</span>
        {#if game.qualityRatio == null}
            <span class="badge quiet">nieuw</span>
        {:else}
            <span
                class="badge"
                title="Kwaliteit {Math.round(game.qualityRatio)}%"
                >👍 {Math.round(game.qualityRatio)}%</span
            >
        {/if}
    </span>
    <span class="row">
        <DifficultyStars mode={game.gameMode} />
        <span class="museum" title={ART_SOURCES[game.source].name}
            >{ART_SOURCES[game.source].shortName}</span
        >
    </span>
    <span class="meta"
        >{game.playCount > 0
            ? `${game.playCount}× gespeeld`
            : 'Nog niet gespeeld'}</span
    >
    <span class="meta">Hoogste score: {game.spyScore}</span>
    <span class="play">Speel</span>
</a>

<style>
    .puzzle {
        display: flex;
        flex-direction: column;
        gap: 6px;
        padding: 34px 34px 24px;
        border-radius: 22px;
        border: 1px solid var(--button-border);
        background-color: var(--color-secondary);
        color: inherit;
        text-decoration: none;
        transition:
            border-color 180ms ease,
            transform 180ms ease;
    }

    .puzzle:hover,
    .puzzle:focus-visible {
        border-color: var(--color-primary);
        outline: none;
        transform: translateY(-2px);
    }

    .top,
    .row {
        display: flex;
        align-items: flex-start;
        justify-content: space-between;
        gap: 10px;
    }

    .row {
        align-items: center;
        margin: 4px 0;
    }

    .hint {
        font-family: var(--font-main), serif;
        font-size: 1.5rem;
        font-weight: 500;
        text-transform: capitalize;
        overflow-wrap: anywhere;
        color: var(--text-primary);
    }

    .badge,
    .museum {
        flex-shrink: 0;
        font-family: var(--font-secondary), sans-serif;
        white-space: nowrap;
        border-radius: 999px;
        padding: 3px 10px;
    }

    .badge {
        font-size: 0.82rem;
        font-weight: 700;
        color: var(--text-primary);
        border: 1px solid var(--secondary-border);
    }

    .badge.quiet {
        font-weight: 500;
        color: var(--text-tertiary);
    }

    .museum {
        font-size: 0.75rem;
        font-weight: 600;
        letter-spacing: 0.04em;
        color: var(--text-eyebrow);
        background-color: var(--color-bg-base);
    }

    .meta {
        font-family: var(--font-secondary), sans-serif;
        font-size: 0.9rem;
        color: var(--text-tertiary);
    }

    .play {
        align-self: flex-start;
        margin-top: 10px;
        padding: 10px 18px;
        min-width: 100px;
        box-sizing: border-box;
        text-align: center;
        border-radius: 20px;
        font-family: var(--font-secondary), sans-serif;
        font-weight: 700;
        color: var(--color-secondary);
        background-color: var(--color-primary);
        box-shadow: 0 10px 20px
            color-mix(in srgb, var(--color-primary) 28%, transparent);
    }

    @media (prefers-reduced-motion: reduce) {
        .puzzle {
            transition: none;
        }
    }
</style>

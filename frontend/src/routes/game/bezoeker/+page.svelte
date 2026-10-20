<script lang="ts">
    import { goto } from '$app/navigation'
    import { resolve } from '$app/paths'
    import { onMount } from 'svelte'
    import { api } from '#lib/api/client.ts'
    import {
        GAME_MODES,
        type GameMode,
        type GameSummary,
    } from '#lib/api/types.ts'
    import MuseumPicker from '#lib/components/hub/MuseumPicker.svelte'
    import DifficultyStars from '#lib/components/ui/DifficultyStars.svelte'
    import PageHeader from '#lib/components/ui/PageHeader.svelte'
    import PuzzleCard from '#lib/components/visitor/PuzzleCard.svelte'
    import { selectedSource } from '#lib/selectedSource.svelte.ts'

    const DAILY_GAME_ID = 1
    const MODE_LABELS: Record<GameMode, string> = {
        makkelijk: 'Makkelijk',
        gemiddeld: 'Normaal',
        moeilijk: 'Moeilijk',
    }
    const SORTS = {
        newest: {
            label: 'Nieuwste',
            compare: (a: GameSummary, b: GameSummary) =>
                b.createdAt.localeCompare(a.createdAt),
        },
        popular: {
            label: 'Populariteit',
            compare: (a: GameSummary, b: GameSummary) =>
                b.playCount - a.playCount,
        },
        quality: {
            label: 'Kwaliteit',
            compare: (a: GameSummary, b: GameSummary) =>
                (b.qualityRatio ?? -1) - (a.qualityRatio ?? -1),
        },
        difficulty: {
            label: 'Moeilijkheid',
            compare: (a: GameSummary, b: GameSummary) =>
                GAME_MODES.indexOf(a.gameMode) - GAME_MODES.indexOf(b.gameMode),
        },
    }

    let games = $state<GameSummary[] | null>(null)
    let failed = $state(false)
    let sort = $state<keyof typeof SORTS>('newest')
    let modes = $state<GameMode[]>([])
    let search = $state('')
    let listOpen = $state(true)

    const museumGames = $derived(
        (games ?? []).filter((game) => game.source === selectedSource.value),
    )
    const shown = $derived(
        museumGames
            .filter(
                (game) => modes.length === 0 || modes.includes(game.gameMode),
            )
            .filter((game) =>
                game.hint.toLowerCase().includes(search.trim().toLowerCase()),
            )
            .sort(SORTS[sort].compare),
    )

    onMount(async () => {
        try {
            games = await api.listGames()
        } catch (error) {
            console.error(error)
            failed = true
        }
    })

    function playRandom(mode: GameMode) {
        const pool = museumGames.filter((game) => game.gameMode === mode)
        const game = pool[Math.floor(Math.random() * pool.length)]
        if (game)
            void goto(
                resolve('/game/bezoeker/[gameId=gameId]', {
                    gameId: game.gameId,
                }),
            )
    }
</script>

<div class="page">
    <PageHeader />
    <main>
        <h1>Kies een puzzel</h1>
        <MuseumPicker />

        <div class="modes">
            <section class="mode-card">
                <h2>Willekeurige clue</h2>
                <p>Laat Canvas Clues een puzzel voor je kiezen</p>
                <div class="buttons">
                    {#each GAME_MODES as mode (mode)}
                        <button
                            type="button"
                            disabled={!museumGames.some(
                                (game) => game.gameMode === mode,
                            )}
                            onclick={() => playRandom(mode)}
                            >{MODE_LABELS[mode]}</button
                        >
                    {/each}
                </div>
            </section>
            <section class="mode-card">
                <h2>Dagelijkse clue</h2>
                <p>Speel de dagelijkse hint</p>
                <div class="buttons">
                    <button
                        type="button"
                        onclick={() =>
                            goto(
                                resolve('/game/bezoeker/[gameId=gameId]', {
                                    gameId: DAILY_GAME_ID,
                                }),
                            )}>Speel</button
                    >
                </div>
            </section>
        </div>

        <button
            type="button"
            class="toggle"
            aria-expanded={listOpen}
            onclick={() => (listOpen = !listOpen)}
            >{listOpen ? 'Verberg puzzels' : 'Bekijk alle puzzels'}</button
        >

        <div class="collapse" class:open={listOpen}>
            <div class="collapse-inner">
                <section class="list">
                    <div class="filters">
                        <label class="sort">
                            Sorteer op:
                            <select bind:value={sort}>
                                {#each Object.entries(SORTS) as [key, { label }] (key)}
                                    <option value={key}>{label}</option>
                                {/each}
                            </select>
                        </label>
                        <div
                            class="mode-filter"
                            role="group"
                            aria-label="Moeilijkheid"
                        >
                            {#each GAME_MODES as mode (mode)}
                                <label
                                    class:active={modes.includes(mode)}
                                    title={mode}
                                >
                                    <input
                                        type="checkbox"
                                        value={mode}
                                        bind:group={modes}
                                    />
                                    <DifficultyStars {mode} />
                                </label>
                            {/each}
                        </div>
                        <input
                            class="search"
                            type="search"
                            placeholder="Zoek op hint..."
                            bind:value={search}
                        />
                    </div>
                    <div class="scroll">
                        {#if failed}
                            <p class="empty">
                                De puzzels konden niet worden geladen.
                            </p>
                        {:else if games && shown.length === 0}
                            <p class="empty">
                                Geen puzzels gevonden voor dit museum.
                            </p>
                        {:else}
                            <div class="grid">
                                {#each shown as game (game.gameId)}
                                    <PuzzleCard {game} />
                                {/each}
                            </div>
                        {/if}
                    </div>
                </section>
            </div>
        </div>
    </main>
</div>

<style>
    .page {
        min-height: 100vh;
        background-color: var(--color-bg-base);
    }

    main {
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 1.5rem;
        padding: 20px 20px 32px;
    }

    h1 {
        margin: 18px 0 0;
        font-family: var(--font-main), serif;
        font-size: 3rem;
        font-weight: 500;
        color: var(--text-primary);
    }

    main > :global(.museum-picker) {
        margin-bottom: 0;
    }

    .modes {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        gap: 1.25rem;
    }

    .mode-card {
        display: flex;
        flex-direction: column;
        width: 350px;
        min-height: 220px;
        box-sizing: border-box;
        padding: 34px 34px 24px;
        border-radius: 22px;
        border: 1px solid var(--button-border);
        background-color: var(--color-secondary);
    }

    .mode-card h2 {
        margin: 0 0 14px;
        font-family: var(--font-main), serif;
        font-size: 1.5rem;
        font-weight: 500;
        color: var(--text-primary);
    }

    .mode-card p {
        flex: 1;
        margin: 0;
        font-family: var(--font-secondary), sans-serif;
        color: var(--text-tertiary);
    }

    .buttons {
        display: flex;
        gap: 10px;
    }

    .buttons button,
    .toggle {
        min-width: 100px;
        padding: 10px 18px;
        border: none;
        border-radius: 20px;
        font-family: var(--font-secondary), sans-serif;
        font-weight: 700;
        color: var(--color-secondary);
        background-color: var(--color-primary);
        cursor: pointer;
        box-shadow: 0 10px 20px
            color-mix(in srgb, var(--color-primary) 28%, transparent);
        transition:
            transform 180ms ease,
            box-shadow 180ms ease,
            opacity 180ms ease;
    }

    .buttons button:hover:not(:disabled),
    .toggle:hover,
    .toggle:focus-visible {
        transform: translateY(-1px);
        box-shadow: 0 14px 24px
            color-mix(in srgb, var(--color-primary) 38%, transparent);
    }

    .buttons button:disabled {
        opacity: 0.4;
        cursor: not-allowed;
        box-shadow: none;
    }

    .toggle {
        min-width: 150px;
    }

    .collapse {
        display: grid;
        grid-template-rows: 0fr;
        width: 100%;
        opacity: 0;
        transition:
            grid-template-rows 360ms ease,
            opacity 280ms ease;
    }

    .collapse.open {
        grid-template-rows: 1fr;
        opacity: 1;
    }

    .collapse-inner {
        min-height: 0;
        overflow: hidden;
        display: flex;
        justify-content: center;
    }

    .list {
        display: flex;
        flex-direction: column;
        width: min(1120px, 92vw);
        height: min(64vh, 760px);
        min-height: 420px;
        box-sizing: border-box;
        padding: 18px;
        border-radius: 28px;
        border: 1px solid var(--scrollbox-border-secondary);
        background: var(--scrolbox-background-gradient);
        box-shadow: 0 18px 38px var(--secondary-shadow);
    }

    .filters {
        display: grid;
        grid-template-columns: 1fr auto 1fr;
        align-items: center;
        gap: 1rem;
        padding: 0 0.72rem 0.9rem;
        font-family: var(--font-secondary), sans-serif;
        color: var(--text-primary);
    }

    .sort {
        display: flex;
        align-items: center;
        gap: 8px;
    }

    select,
    .search {
        font: inherit;
        font-size: 0.9rem;
        padding: 12px 16px;
        border: 1px solid var(--secondary-border);
        border-radius: 20px;
        outline: none;
        color: var(--text-primary);
        background-color: var(--color-secondary);
        transition: border-color 180ms ease;
    }

    select:focus,
    .search:focus {
        border-color: var(--color-primary);
    }

    .search {
        justify-self: end;
        width: 160px;
        min-width: 0;
    }

    .mode-filter {
        display: flex;
        gap: 0.5rem;
    }

    .mode-filter label {
        position: relative;
        display: inline-flex;
        align-items: center;
        justify-content: center;
        min-width: 64px;
        padding: 4px 8px;
        border-radius: 14px;
        border: 1px solid var(--secondary-border);
        opacity: 0.6;
        cursor: pointer;
        transition:
            opacity 160ms ease,
            border-color 160ms ease;
    }

    .mode-filter label:hover {
        opacity: 0.75;
    }

    .mode-filter label:has(:focus-visible) {
        outline: 2px solid var(--color-primary);
        outline-offset: 2px;
    }

    .mode-filter label.active {
        opacity: 1;
        border-color: var(--color-primary);
        background-color: var(--color-secondary);
    }

    .mode-filter input {
        position: absolute;
        opacity: 0;
        pointer-events: none;
    }

    .mode-filter :global(svg) {
        display: block;
        height: 18px;
        width: auto;
    }

    .scroll {
        flex: 1;
        min-height: 0;
        overflow-y: auto;
        padding: 0.72rem;
        border-radius: 24px;
        border: 1px solid var(--scrollbox-border);
        background-color: var(--color-bg-base);
        box-shadow: inset 0 1px 0 var(--shadow-scrollbox);
        scrollbar-color: var(--scrollbar-color) transparent;
    }

    .grid {
        display: grid;
        grid-template-columns: repeat(3, minmax(0, 1fr));
        align-items: start;
        gap: 1.1rem;
    }

    .empty {
        margin: 3rem 0;
        text-align: center;
        font-family: var(--font-secondary), sans-serif;
        color: var(--text-tertiary);
    }

    @media (max-width: 1200px) {
        .grid {
            grid-template-columns: repeat(2, minmax(0, 1fr));
        }
    }

    @media (max-width: 760px) {
        .grid {
            grid-template-columns: 1fr;
        }

        .filters {
            grid-template-columns: 1fr;
            justify-items: center;
        }

        .search {
            justify-self: center;
        }
    }
</style>

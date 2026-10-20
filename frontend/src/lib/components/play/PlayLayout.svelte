<script lang="ts">
    import type { Snippet } from 'svelte'
    import type { Card } from '#lib/api/types.ts'
    import PageHeader from '#lib/components/ui/PageHeader.svelte'
    import { ART_SOURCES } from '#lib/sources.ts'
    import ArtInfo from './ArtInfo.svelte'

    let {
        phase,
        activeCard,
        credit,
        children,
        sidebar,
    }: {
        phase: string
        activeCard: Card | null
        credit: Card['source'] | null
        children: Snippet
        sidebar: Snippet
    } = $props()
</script>

<div class="page">
    <PageHeader {phase} />
    <main>
        <div class="layout">
            <div class="column">
                {#if activeCard}
                    {#key activeCard.id}
                        <ArtInfo card={activeCard} />
                    {/key}
                {/if}
            </div>
            <div class="board">{@render children()}</div>
            <div class="column sidebar">{@render sidebar()}</div>
        </div>
        {#if credit}
            <p class="credit">
                Kunstwerken:
                <a
                    href={ART_SOURCES[credit].url}
                    target="_blank"
                    rel="external noopener noreferrer"
                    >{ART_SOURCES[credit].name}</a
                >
            </p>
        {/if}
    </main>
</div>

<style>
    .page {
        display: flex;
        flex-direction: column;
        height: 100vh;
        overflow: hidden;
        background-color: var(--color-bg-base);
    }

    main {
        flex: 1;
        min-height: 0;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .layout {
        --layout-height: 85vh;
        --layout-padding: 20px;
        display: grid;
        grid-template-columns: auto 1fr auto;
        align-items: start;
        gap: 32px;
        height: var(--layout-height);
        box-sizing: border-box;
        padding: var(--layout-padding);
    }

    .column {
        width: 17vw;
        min-width: 280px;
        max-width: 400px;
    }

    .sidebar {
        display: flex;
        flex-direction: column;
        gap: 14px;
    }

    .board {
        justify-self: center;
        width: calc(var(--layout-height) - var(--layout-padding) * 2);
        max-width: 100%;
    }

    .credit {
        position: fixed;
        left: 24px;
        bottom: 16px;
        margin: 0;
        font-family: var(--font-secondary), sans-serif;
        font-size: 0.75rem;
        color: var(--text-tertiary);
    }

    .credit a {
        color: inherit;
    }
</style>

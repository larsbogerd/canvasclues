<script lang="ts">
    import type { GameMode } from '#lib/api/types.ts'

    const MODES: GameMode[] = ['makkelijk', 'gemiddeld', 'moeilijk']

    let { value = $bindable() }: { value: GameMode } = $props()
</script>

<span class="modes" role="radiogroup" aria-label="Moeilijkheid">
    {#each MODES as mode (mode)}
        <button
            type="button"
            class:active={value === mode}
            role="radio"
            aria-checked={value === mode}
            onclick={(event) => {
                event.stopPropagation()
                value = mode
            }}
            onkeydown={(event) => event.stopPropagation()}>{mode}</button
        >
    {/each}
</span>

<style>
    .modes {
        display: inline-flex;
        gap: 0.5rem;
        margin-top: 0.5rem;
        flex-wrap: wrap;
    }

    button {
        font-family: var(--font-secondary), sans-serif;
        font-size: 0.85rem;
        font-weight: 600;
        padding: 0.4rem 0.9rem;
        border-radius: 999px;
        border: 1px solid var(--button-border);
        background-color: var(--color-secondary);
        color: var(--text-secondary);
        cursor: pointer;
        text-transform: capitalize;
        transition:
            border-color 160ms ease,
            background-color 160ms ease,
            color 160ms ease;
    }

    button:hover,
    button:focus-visible {
        border-color: var(--color-primary);
        outline: none;
    }

    button.active {
        background-color: var(--color-primary);
        border-color: var(--color-primary);
        color: var(--color-bg-base);
    }
</style>

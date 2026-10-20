<script lang="ts">
    import { tick } from 'svelte'
    import MuseumLogo from '#lib/components/ui/MuseumLogo.svelte'
    import { selectedSource } from '#lib/selectedSource.svelte.ts'
    import { ART_SOURCES, SOURCE_KEYS } from '#lib/sources.ts'

    const options: HTMLButtonElement[] = $state([])

    // Radio-group keyboard behaviour: one tab stop, arrow keys move the selection.
    async function move(step: number) {
        const index = SOURCE_KEYS.indexOf(selectedSource.value)
        const next = (index + step + SOURCE_KEYS.length) % SOURCE_KEYS.length
        selectedSource.value = SOURCE_KEYS[next]
        await tick()
        options[next]?.focus()
    }

    function onkeydown(event: KeyboardEvent) {
        const step = {
            ArrowRight: 1,
            ArrowDown: 1,
            ArrowLeft: -1,
            ArrowUp: -1,
        }[event.key]
        if (!step) return
        event.preventDefault()
        void move(step)
    }
</script>

<div class="museum-picker">
    <div
        class="options"
        role="radiogroup"
        aria-label="Museum"
        tabindex="-1"
        {onkeydown}
    >
        {#each SOURCE_KEYS as key, i (key)}
            {@const selected = key === selectedSource.value}
            <button
                bind:this={options[i]}
                type="button"
                class="option"
                class:selected
                role="radio"
                aria-checked={selected}
                tabindex={selected ? 0 : -1}
                title={ART_SOURCES[key].name}
                onclick={() => (selectedSource.value = key)}
            >
                <MuseumLogo source={key} />
            </button>
        {/each}
    </div>

    <span class="selected-name" aria-hidden="true"
        >{ART_SOURCES[selectedSource.value].name}</span
    >
</div>

<style>
    .museum-picker {
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 0.6rem;
        margin-bottom: 1.75rem;
    }

    .options {
        display: flex;
        gap: 0.75rem;
    }

    .option {
        width: 104px;
        height: 72px;
        padding: 12px 18px;
        border: 1px solid transparent;
        border-radius: 16px;
        background: transparent;
        color: var(--text-eyebrow);
        opacity: 0.45;
        cursor: pointer;
        transition:
            opacity 150ms ease,
            background-color 150ms ease,
            box-shadow 150ms ease,
            color 150ms ease;
    }

    .option:hover {
        opacity: 0.8;
    }

    .option.selected {
        opacity: 1;
        color: var(--text-primary);
        background-color: var(--color-secondary);
        border-color: var(--button-border);
        box-shadow: 0 8px 20px var(--secondary-shadow);
    }

    .option:focus-visible {
        outline: 2px solid var(--color-primary);
        outline-offset: 3px;
    }

    .selected-name {
        font-family: var(--font-secondary), sans-serif;
        font-size: 0.85rem;
        color: var(--text-tertiary);
    }

    @media (prefers-reduced-motion: reduce) {
        .option {
            transition: none;
        }
    }
</style>

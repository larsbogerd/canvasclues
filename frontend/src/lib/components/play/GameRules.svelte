<script lang="ts">
    import type { Snippet } from 'svelte'
    import { slide } from 'svelte/transition'

    let { title, children }: { title: string; children: Snippet } = $props()

    let open = $state(true)
</script>

<section class="rules">
    <button type="button" aria-expanded={open} onclick={() => (open = !open)}>
        <span>Spelregels</span>
        <span class="chevron" class:open aria-hidden="true">⌄</span>
    </button>
    {#if open}
        <div class="body" transition:slide={{ duration: 180 }}>
            <h2 class="modal-title">{title}</h2>
            <div class="modal-body">{@render children()}</div>
        </div>
    {/if}
</section>

<style>
    .rules {
        width: 100%;
        border-radius: 20px;
        background: var(--color-secondary);
        box-shadow: 0 2px 8px var(--primary-shadow);
        overflow: hidden;
    }

    button {
        display: flex;
        align-items: center;
        justify-content: space-between;
        width: 100%;
        padding: 14px 20px;
        background: transparent;
        border: none;
        font-family: var(--font-secondary), sans-serif;
        font-size: 1rem;
        font-weight: 600;
        color: var(--text-primary);
        cursor: pointer;
    }

    .chevron {
        font-size: 1.1rem;
        line-height: 1;
        transition:
            transform 180ms ease,
            color 160ms ease;
    }

    .chevron.open {
        transform: rotate(180deg);
    }

    button:hover .chevron,
    button:focus-visible .chevron {
        color: var(--color-primary);
    }

    .body {
        padding: 0 20px 16px;
        max-height: 50vh;
        overflow-y: auto;
    }
</style>

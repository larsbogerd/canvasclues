<script lang="ts">
    import type { Snippet } from 'svelte'
    import { fade, scale } from 'svelte/transition'
    import { cubicOut } from 'svelte/easing'

    let {
        open = $bindable(false),
        children,
    }: { open?: boolean; children: Snippet } = $props()

    function close() {
        open = false
    }

    function onkeydown(event: KeyboardEvent) {
        if (open && event.key === 'Escape') close()
    }
</script>

<svelte:window {onkeydown} />

{#if open}
    <div
        class="overlay"
        role="presentation"
        onclick={(event) => {
            if (event.target === event.currentTarget) close()
        }}
        transition:fade={{ duration: 200 }}
    >
        <div
            class="popup"
            role="dialog"
            aria-modal="true"
            transition:scale={{ start: 0.96, duration: 220, easing: cubicOut }}
        >
            <button
                type="button"
                class="close"
                aria-label="Sluiten"
                onclick={close}>✕</button
            >
            {@render children()}
        </div>
    </div>
{/if}

<style>
    .overlay {
        position: fixed;
        inset: 0;
        background: var(--modal-background-blur);
        z-index: 10;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .popup {
        min-width: 320px;
        max-width: 70vw;
        max-height: 90vh;
        overflow-y: auto;
        background: var(--color-secondary);
        border-radius: 20px;
        position: relative;
        box-shadow: 0 8px 32px var(--secondary-shadow);
    }

    .close {
        position: absolute;
        top: clamp(10px, 1.5vw, 20px);
        right: clamp(10px, 1.5vw, 20px);
        background: none;
        border: none;
        font-size: clamp(1rem, 1.2vw, 1.4rem);
        color: var(--text-eyebrow);
        cursor: pointer;
        transition: color 180ms ease;
    }

    .close:hover {
        color: var(--text-primary);
    }
</style>

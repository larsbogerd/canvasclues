<script lang="ts">
    import type { Snippet } from 'svelte'

    let { header, children }: { header?: Snippet; children: Snippet } = $props()
</script>

<section class="panel">
    {#if header}
        <div class="header">{@render header()}</div>
    {/if}
    {@render children()}
</section>

<style>
    .panel {
        width: 100%;
        border-radius: 24px;
        border: 1px solid var(--button-border);
        box-shadow: 0 2px 8px var(--primary-shadow);
        background: var(--color-secondary);
        overflow: hidden;
    }

    .header {
        display: flex;
        align-items: center;
        justify-content: center;
        text-align: center;
        padding: 20px 16px;
        border-bottom: 2px solid var(--hint-border-bottom);
    }
</style>

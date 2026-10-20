<script lang="ts">
    import type { Snippet } from 'svelte'
    import type { Card } from '#lib/api/types.ts'

    let { cards, card }: { cards: Card[]; card: Snippet<[Card]> } = $props()
</script>

<div class="board">
    {#each cards as c (c.id)}
        {@render card(c)}
    {/each}
</div>

<style>
    .board {
        display: grid;
        grid-template-columns: repeat(4, 1fr);
        gap: 16px;
        width: 100%;
        align-content: start;
    }
</style>

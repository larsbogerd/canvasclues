<script setup>
</script>
<template>
    <RouterView v-slot="{ Component }">
        <Transition name="page" mode="out-in">
            <component :is="Component" />
        </Transition>
    </RouterView>
</template>

<style scoped>
</style>

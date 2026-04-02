<script setup>
import ButtonBase from "@/components/ButtonBase.vue";
</script>

<template>
  <h1>Spymaster fase</h1>
  <section>
    <p>Als spymaster maak je hints die meerdere kunstwerken met elkaar verbinden.</p>

    <p>
      Selecteer kaarten die volgens jou bij dezelfde hint kunnen horen.
      Hoe meer kaarten je met één hint verbindt,
      hoe meer punten jij en de operatives kunnen verdienen.
    </p>
    <p><strong>Paars</strong> - 3 punten</p>
    <p><strong>Blauw</strong> - 2 punten</p>
    <p><strong>Geel</strong> - 1 punten</p>
    <p><strong>Zwart</strong> - Game over voor de operative</p>
    <button-base id="button">Begrepen</button-base>
  </section>
</template>

<style scoped>
h1{
  text-align: center;
}

#button{
  display: block;
  margin:  8% auto 0;
}

section{
  font-family: var(--font-secondary);
}
</style>
<script setup>
import ButtonBase from "@/components/global-components/ButtonBase.vue";
</script>

<template>
  <section>
    <h1 class="modal-title">Spymaster fase</h1>
    <div class="modal-body">
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
    </div>
    <div class="modal-actions">
      <ButtonBase>Begrepen</ButtonBase>
    </div>
  </section>
</template>

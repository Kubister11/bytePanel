<script setup>

</script>

<template>
  <div class="w-[25%] h-full">
    <div class="flex flex-col w-full gap-5">

      <div class="bg-black bg-opacity-20 flex items-center gap-5 w-full h-[100px] rounded-[10px] p-5" data-aos="fade-left">
        <div class="bg-black rounded-[10px] bg-opacity-30 w-16 h-16 flex items-center justify-center">
          <font-awesome-icon :icon="['fas', 'clock']"  class="text-white size-8 opacity-70"/>
        </div>
        <div>
          <h1 class="text-[#ADC4FF] text-[20px] m-0 leading-tight">Uptime</h1>
          <p class="text-[#D1DEFF] bold-font text-[25px] m-0 leading-tight"></p>
        </div>
      </div>

      <div class="bg-black bg-opacity-20 flex items-center gap-5 w-full h-[100px] rounded-[10px] p-5" data-aos="fade-left">
        <div class="bg-black rounded-[10px] bg-opacity-30 w-16 h-16 flex items-center justify-center">
          <font-awesome-icon :icon="['fas', 'microchip']"  class="text-white size-8 opacity-70"/>
        </div>
        <div>
          <h1 class="text-[#ADC4FF] text-[20px] m-0 leading-tight">CPU Load</h1>
          <p class="text-[#D1DEFF] bold-font text-[25px] m-0 leading-tight">% <span class="text-[18px] opacity-50">/ %</span></p>
        </div>
      </div>

      <div class="bg-black bg-opacity-20 flex items-center gap-5 w-full h-[100px] rounded-[10px] p-5" data-aos="fade-left">
        <div class="bg-black rounded-[10px] bg-opacity-30 w-16 h-16 flex items-center justify-center">
          <font-awesome-icon :icon="['fas', 'memory']"  class="text-white size-8 opacity-70"/>
        </div>
        <div>
          <h1 class="text-[#ADC4FF] text-[20px] m-0 leading-tight">Memory Usage</h1>
          <p class="text-[#D1DEFF] bold-font text-[25px] m-0 leading-tight">GB <span class="text-[18px] opacity-50">/ GB</span></p>
        </div>
      </div>

      <div class="bg-black bg-opacity-20 flex items-center gap-5 w-full h-[100px] rounded-[10px] p-5" data-aos="fade-left">
        <div class="bg-black rounded-[10px] bg-opacity-30 w-16 h-16 flex items-center justify-center">
          <font-awesome-icon :icon="['fas', 'floppy-disk']"  class="text-white size-8 opacity-70"/>
        </div>
        <div>
          <h1 class="text-[#ADC4FF] text-[20px] m-0 leading-tight">Disk</h1>
          <p class="text-[#D1DEFF] bold-font text-[25px] m-0 leading-tight">GB <span class="text-[18px] opacity-50">/ GB</span></p>
        </div>
      </div>

    </div>

  </div>
</template>

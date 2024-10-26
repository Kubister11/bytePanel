<template>
  <div>
    <div class="bg-black flex w-[350px] h-[170px] rounded-[10px] bg-opacity-20 cursor-pointer gap-1">
      <div class="h-full w-[15px] bg-[#FFD900] rounded-l-[10px] bg-opacity-35"></div>

      <div class="h-full w-full flex-col flex p-3 gap-3">
        <h1 class="text-[#ADC4FF] text-[32px] bold-font leading-none">BOXPVP-1</h1>

        <div class="flex w-fit items-center flex-col gap-2 ml-3">
          <div class="flex items-center justify-center flex-col">
            <p class="text-white">CPU Load:</p>
            <p class="text-[#D1DEFF] bold-font">100% <span class="opacity-40">/ 200%</span></p>
          </div>
          <div class="flex items-center justify-center flex-col">
            <p class="text-white">Memory Usage:</p>
            <p class="text-[#D1DEFF] bold-font">4.56GB <span class="opacity-40">/ 7GB</span></p>
          </div>
        </div>
      </div>

      <div class="h-full flex-col flex p-3 gap-3">
        <div class="h-full justify-end flex">
          <font-awesome-icon :icon="['fas', 'server']" class="text-white size-[70px] opacity-15" />
        </div>
        <div class="h-full flex items-end">
          <div class="flex gap-1.5">
            <div class="bg-[#009DFF] bg-opacity-20 flex items-center justify-center w-[40px] h-[40px] rounded-l-[10px]">
              <font-awesome-icon :icon="['fas', 'power-off']" class="text-white size-[25px] opacity-70" />
            </div>
            <div class="bg-[#009DFF] bg-opacity-20 flex items-center justify-center w-[40px] h-[40px]">
              <font-awesome-icon :icon="['fas', 'rotate']" class="text-white size-[25px] opacity-70" />
            </div>
            <div class="bg-[#009DFF] bg-opacity-20 flex items-center justify-center w-[40px] h-[40px] rounded-r-[10px]">
              <font-awesome-icon :icon="['far', 'circle-stop']" class="text-white size-[25px] opacity-70" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="js">

import {onMounted, ref} from "vue";


const servers = ref([{
  name: "BOXPVP_1",
  cpuLoad: 100,
  memoryUsage: 4.56,
  diskUsage: 60,
  online: true
}])


</script>
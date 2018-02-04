package com.com.scheduler;

public class Scheduler {

    public Scheduling_Scheme BWS_SA() {
        Scheduling_Scheme init_scheme = new Scheduling_Scheme();
        Scheduler sc = new Scheduler();

        init_scheme = sc.calculate_init_mapping();



        Components_cost components_cost = sc.calculate_communication_execution_cost(init_scheme);
        Critical_path critical_path_new = new Critical_path();
        critical_path_new.critical_modules = critical_path_new.calculate_critical_path(components_cost.link_delay, components_cost.exe_time);
        critical_path_new.ED = critical_path_new.calculate_ED(components_cost.link_delay, components_cost.exe_time, critical_path_new.critical_modules);
        Critical_path critical_path_old = new Critical_path();
        critical_path_old.ED = 0;
        int[][] new_scheme;
        while (critical_path_new.ED - critical_path_old.ED > threshold) {
            new_scheme = sc.critical_module_mapping(init_scheme, critical_path_new.critical_modules);
            new_scheme = sc.non_critical_module_mapping(init_scheme, critical_path_new.critical_modules);
            components_cost = sc.calculate_communication_execution_cost(new_scheme);
            critical_path_old.critical_modules = critical_path_new.critical_modules;
            critical_path_old.ED = critical_path_new.ED;
            critical_path_new.critical_modules = critical_path_new.calculate_critical_path(components_cost.link_delay, components_cost.exe_time);
            critical_path_new.ED = critical_path_new.calculate_ED(components_cost.link_delay, components_cost.exe_time, critical_path_new.critical_modules);
        }
    }

}

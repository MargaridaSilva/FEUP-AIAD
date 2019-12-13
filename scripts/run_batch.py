import subprocess

for num_plants in [2, 15]:
    for num_predators in [6, 10]:
        for num_preys in [6, 10]:
            for ratio_preds in [0.5]:
                for ratio_preys in [0.5]:
                    for e_preds in [0.01]:
                        for e_preys in [0.01]:
                            subprocess.call(['./launch.sh', str(num_plants), str(num_preys), str(num_predators), str(ratio_preds), str(ratio_preys), str(e_preds), str(e_preys)])


# num_plants : 2, 15, 60
# num_preys: 2, 6, 10, 14, 18, 22, 26, 30, 34, 38, 40
# num_predators: 2, 6, 10, 14, 18, 22, 26, 30, 34, 38, 40
# pred_ratio: 0.10, 0.5, 0.90
# prey_ratio: 0.10, 0.5, 0.90
# exp_predators : 0.005, 0.015, 0.05
# exp_preys : 0.005, 0.015, 0.05
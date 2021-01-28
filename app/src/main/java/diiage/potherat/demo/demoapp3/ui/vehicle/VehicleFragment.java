package diiage.potherat.demo.demoapp3.ui.vehicle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import diiage.potherat.demo.demoapp3.common.EventObserver;
import diiage.potherat.demo.demoapp3.databinding.FragmentVehicleBinding;
import diiage.potherat.demo.demoapp3.ui.edit.QuoteEditFragmentDirections;

@AndroidEntryPoint
public class VehicleFragment extends Fragment {

    @Inject
    VehicleViewModel viewModel;
    private FragmentVehicleBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentVehicleBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new ViewModelProvider(this,getDefaultViewModelProviderFactory()).get(VehicleViewModel.class);
    }

    @Override
    public void onStart(){
        super.onStart();
        ready();
    }

    private void ready(){
        if (binding != null && viewModel != null){

            binding.setLifecycleOwner(getViewLifecycleOwner());
            binding.setViewmodel(viewModel);

            binding.btnValid.setOnClickListener(view -> {
                viewModel.getVehicleById();
            });

            viewModel.getVehicle().observe(getViewLifecycleOwner(), vehicle -> {
                if (vehicle != null){
                    binding.vehicleError.setVisibility(View.GONE);
                    binding.vehicleResultName.setVisibility(View.VISIBLE);
                    binding.vehicleResultModel.setVisibility(View.VISIBLE);
                    binding.vehicleResultName.setText(vehicle.name);
                    binding.vehicleResultModel.setText(vehicle.model);
                }else {
                    binding.vehicleError.setVisibility(View.VISIBLE);
                    binding.vehicleResultName.setVisibility(View.GONE);
                    binding.vehicleResultModel.setVisibility(View.GONE);
                    binding.vehicleError.setText("Aucun véhicule pour cet id !");
                }
            });

        }
    }
}

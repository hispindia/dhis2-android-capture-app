package org.dhis2afgamis.usescases.teiDashboard.dashboardfragments.relationships;

import androidx.recyclerview.widget.RecyclerView;
import org.dhis2afgamis.databinding.ItemRelationshipBinding;
import org.hisp.dhis.android.core.D2;
import org.hisp.dhis.android.core.D2Manager;
import org.hisp.dhis.android.core.relationship.Relationship;
import org.hisp.dhis.android.core.trackedentity.TrackedEntityAttributeValue;

import java.util.List;

/**
 * QUADRAM. Created by ppajuelo on 05/12/2017.
 */

public class RelationshipViewHolder extends RecyclerView.ViewHolder {

    private final ItemRelationshipBinding binding;

    public RelationshipViewHolder(ItemRelationshipBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(RelationshipPresenter presenter, RelationshipViewModel relationships) {

        Relationship relationship = relationships.relationship();

        boolean from = relationships.relationshipDirection() == RelationshipViewModel.RelationshipDirection.FROM;

        binding.teiRelationshipLink.setOnClickListener(view -> presenter.openDashboard(relationships.teiUid()));

        binding.setPresenter(presenter);
        binding.setRelationship(relationship);
        String relationshipNameText = from ? relationships.relationshipType().toFromName() : relationships.relationshipType().fromToName();
        binding.relationshipName.setText(relationshipNameText != null ? relationshipNameText : relationships.relationshipType().displayName());

        //@Sou set list relation
        if (from && relationships.fromAttributes() != null) {
            setAttributes(relationships.fromAttributes());
        } else if (!from && relationships.toAttributes() != null) {
            setAttributes(relationships.toAttributes());
        }
    }


    //@Sou to be check for relation ship list
    private void setAttributes(List<TrackedEntityAttributeValue> trackedEntityAttributeValueModels) {
        D2 d2 = D2Manager.getD2();
        List<TrackedEntityAttributeValue> tei_list=d2.trackedEntityModule().trackedEntityAttributeValues().byTrackedEntityInstance().eq(trackedEntityAttributeValueModels.get(0).trackedEntityInstance()).byTrackedEntityAttribute().in("kKdnMfsT1SF","Ju7MEdETxsV").blockingGet();
        if (trackedEntityAttributeValueModels.size() > 1)
            binding.setTeiName(String.format("%s %s", trackedEntityAttributeValueModels.get(0).value(), trackedEntityAttributeValueModels.get(1).value()));
        else if (!trackedEntityAttributeValueModels.isEmpty())
//            binding.setTeiName(trackedEntityAttributeValueModels.get(0).value());
            if(tei_list.size()>1)
            {
                binding.setTeiName(String.format("%s %n%s", tei_list.get(0).value(), tei_list.get(1).value()));
            }
        else if(tei_list.size()==1)
            {
                binding.setTeiName(tei_list.get(0).value());
            }
        else
            {

            }

        else
            binding.setTeiName("-");
        binding.executePendingBindings();
    }
}

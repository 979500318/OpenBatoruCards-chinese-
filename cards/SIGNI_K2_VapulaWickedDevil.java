package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_K2_VapulaWickedDevil extends Card {

    public SIGNI_K2_VapulaWickedDevil()
    {
        setImageSets("WX24-P4-086");

        setOriginalName("凶魔　ヴァプラ");
        setAltNames("キョウマヴァプラ Kyouma Vapura");
        setDescription("jp",
                "@A $T1 %X @[他の＜悪魔＞のシグニ１体を場からトラッシュに置く]@：あなたのトラッシュから黒のシグニ１枚を対象とし、それを場に出す。"
        );

        setName("en", "Vapula, Wicked Devil");
        setDescription("en",
                "@A $T1 %X @[Put another 1 of your <<Devil>> SIGNI from the field into the trash]@: Target 1 black SIGNI from your trash, and put it onto the field."
        );

		setName("zh_simplified", "凶魔 瓦布拉");
        setDescription("zh_simplified", 
                "@A $T1 %X其他的<<悪魔>>精灵1只从场上放置到废弃区:从你的废弃区把黑色的精灵1张作为对象，将其出场。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ActionAbility act = registerActionAbility(new AbilityCostList(
                new EnerCost(Cost.colorless(1)),
                new TrashCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.DEVIL).except(cardId))
            ), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withColor(CardColor.BLACK).fromTrash()).get();
            putOnField(target);
        }
    }
}

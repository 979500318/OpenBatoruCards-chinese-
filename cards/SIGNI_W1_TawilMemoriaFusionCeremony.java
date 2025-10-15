package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W1_TawilMemoriaFusionCeremony extends Card {

    public SIGNI_W1_TawilMemoriaFusionCeremony()
    {
        setImageSets("WXDi-P11-051", "WXDi-P11-051P");

        setOriginalName("融合の儀　タウィル//メモリア");
        setAltNames("ユウゴウノギタウィルメモリア Yuugou no Gi Tauiru Memoria");
        setDescription("jp",
                "@C：あなたの場に《融合の儀　ウムル//メモリア》があるかぎり、このシグニとあなたの《融合の儀　ウムル//メモリア》のパワーを＋4000する。\n" +
                "@A @[このシグニと《融合の儀　ウムル//メモリア》１体を場からトラッシュに置く]@：あなたの手札から白か黒のシグニ１枚を場に出す。\n\n" +
                "@C：このカードの上にある《融合せし極門　ウトゥルス//メモリア》は@>@U：このシグニがアタックしたとき、あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。@@を得る。"
        );

        setName("en", "Tawil//Memoria, Fusion Ritual");
        setDescription("en",
                "@C: As long as there is \"Umr//Memoria, Fusion Ritual\" on your field, this SIGNI and \"Umr//Memoria, Fusion Ritual\" on your field get +4000 power.\n" +
                "@A @[Put this SIGNI and an \"Umr//Memoria, Fusion Ritual\" into their owner's trash]@: Put a white or black SIGNI from your hand onto your field.\n\n" +
                "@C: The \"Ut'ulls//Memoria, Fusion Ultra Gate\" on top of this card gains@>@U: Whenever this SIGNI attacks, add target SIGNI with a #G from your trash to your hand."
        );
        
        setName("en_fan", "Tawil//Memoria, Fusion Ceremony");
        setDescription("en_fan",
                "@C: As long as there is \"Umr//Memoria, Fusion Ritual\" on your field, this SIGNI and \"Umr//Memoria, Fusion Ritual\" on your field get +4000 power.\n" +
                "@A @[Put this SIGNI and 1 \"Umr//Memoria, Fusion Ritual\" from the field into the trash]@: Put 1 white or black SIGNI from your hand onto the field.\n\n" +
                "@C: The \"Ut'ulls//Memoria, Fused Ultimate Gate\" on top of this card gains:" +
                "@>@U: Whenever this SIGNI attacks, target 1 SIGNI with #G @[Guard]@ from your trash, and add it to your hand."
        );

		setName("zh_simplified", "融合之仪 塔维尔//回忆");
        setDescription("zh_simplified", 
                "@C :你的场上有《融合の儀　ウムル//メモリア》时，这只精灵和你的《融合の儀　ウムル//メモリア》的力量+4000。\n" +
                "@A 这只精灵和《融合の儀　ウムル//メモリア》1只从场上放置到废弃区:从你的手牌把白色或黑色的精灵1张出场。\n" +
                "@C :这张牌的上面的《融合せし極門　ウトゥルス//メモリア》得到\n" +
                "@>@U 当这只精灵攻击时，从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEff1Cond,
                new TargetFilter().own().SIGNI().or(new TargetFilter().match(cardId), new TargetFilter().withName("融合の儀　ウムル//メモリア")),
                new PowerModifier(4000)
            );

            registerActionAbility(new AbilityCostList(
                new TrashCost(),
                new TrashCost(new TargetFilter().SIGNI().withName("融合の儀　ウムル//メモリア"))
            ), this::onActionEff);

            ConstantAbility cont = registerConstantAbility(new TargetFilter().own().SIGNI().withName("融合せし極門　ウトゥルス//メモリア").over(cardId),
                new AbilityGainModifier(this::onConstEff2ModGetSample)
            );
            cont.setActiveUnderFlags(CardUnderCategory.UNDER);
        }
        
        private ConditionState onConstEff1Cond()
        {
            return new TargetFilter().own().SIGNI().withName("融合の儀　ウムル//メモリア").getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withColor(CardColor.WHITE,CardColor.BLACK).fromHand().playable()).get();
            putOnField(target);
        }

        private Ability onConstEff2ModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = getAbility().getSourceCardIndex().getIndexedInstance().playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            getAbility().getSourceCardIndex().getIndexedInstance().addToHand(target);
        }
    }
}

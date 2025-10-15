package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_R3_LiberatorRilMemoryOfFreedom extends Card {

    public LRIG_R3_LiberatorRilMemoryOfFreedom()
    {
        setImageSets("WXDi-P15-006", "WXDi-P15-006U");

        setOriginalName("自由の記憶　解放者リル");
        setAltNames("ジユウノキオクカイホウシャリル Jiyuu no Kioku Kaihousha Riru");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A $T1 %R %X：あなたのトラッシュから&Rを持つシグニ１枚を対象とし、それを場に出す。\n" +
                "@A $G1 %R0：あなたのトラッシュから＜解放派＞のシグニを２枚まで対象とし、それらをあなたの＜解放派＞のシグニ２体までの下に置く。"
        );

        setName("en", "Liberator Ril, Memory of Freedom");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field.\n@A $T1 %R %X: Put target SIGNI with a &R from your trash onto your field.\n@A $G1 %R0: Put up to two target <<Liberation Division>> SIGNI from your trash under up to two <<Liberation Division>> SIGNI on your field."
        );
        
        setName("en_fan", "Liberator Ril, Memory of Freedom");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and banish it.\n" +
                "@A $T1 %R %X: Target 1 &R SIGNI from your trash, and put it onto the field.\n" +
                "@A $G1 %R0: Target up to 2 <<Liberation Faction>> SIGNI from your trash, and put them under up to 2 <<Liberation Faction>> SIGNI on your field."
        );

		setName("zh_simplified", "自由的记忆 解放者莉露");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其破坏。\n" +
                "@A $T1 %R%X:从你的废弃区把持有[升阶]的精灵1张作为对象，将其出场。\n" +
                "@A $G1 %R0:从你的废弃区把<<解放派>>精灵2张最多作为对象，将这些放置到你的<<解放派>>精灵2只最多的下面。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.RIL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setLevel(3);
        setLimit(6);
        setCoins(4);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 1) + Cost.colorless(1)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);
            act1.setCondition(this::onActionEff1Cond);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
        
        private ConditionState onActionEff1Cond()
        {
            return new TargetFilter(TargetHint.FIELD).own().SIGNI().withUseCondition(UseCondition.RISE).fromTrash().playable().getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withUseCondition(UseCondition.RISE).fromTrash().playable()).get();
            putOnField(target);
        }

        private void onActionEff2()
        {
            DataTable<CardIndex> dataFromTrash = playerTargetCard(0,2, new TargetFilter(TargetHint.UNDER).own().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION).fromTrash());

            if(dataFromTrash.get() != null)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.UNDER).own().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION));

                attach(data,dataFromTrash, CardUnderType.UNDER_GENERIC);
            }
        }
    }
}

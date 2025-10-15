package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_G3_MidorikoTypeAppeal extends Card {

    public LRIG_G3_MidorikoTypeAppeal()
    {
        setImageSets("WX24-P1-014", "WX24-P1-014U");

        setOriginalName("讃型　緑姫");
        setAltNames("サンガタミドリコ Sangata Midoriko");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの＜地獣＞のシグニ１体を対象とし、ターン終了時まで、それのパワーを＋3000し、それは@>@U：このシグニがアタックしたとき、パワーがこのシグニのパワーの半分以下の対戦相手のシグニ１体を対象とし、それをバニッシュする。@@を得る。\n" +
                "@A $G1 @[@|ワナ|@]@ %G0：あなたのエナゾーンからカードを３枚まで対象とし、それらを手札に加える。その後、この方法で手札に加えたカード１枚につき対戦相手のエナゾーンからカードを１枚まで対象とし、それらを手札に戻す。"
        );

        setName("en", "Midoriko, Type Appeal");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your <<Earth Beast>> SIGNI, and until end of turn, it gets +3000 power and:" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI with power equal to or less than half this SIGNI's, and banish it.@@" +
                "@A @[@|Wanna|@]@ $G1 %G0: Target up to 3 cards from your ener zone, and add them to your hand. Then, target up to 1 card from your opponent's ener zone for each card added to your hand this way, and return them to their hand."
        );

		setName("zh_simplified", "赞型 绿姬");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的<<地獣>>精灵1只作为对象，直到回合结束时为止，其的力量+3000，其得到\n" +
                "@>@U :当这只精灵攻击时，力量在这只精灵的力量的一半以下的对战对手的精灵1只作为对象，将其破坏。@@\n" +
                "@A $G1 祈望%G0:从你的能量区把牌3张最多作为对象，将这些加入手牌。然后，依据这个方法加入手牌的牌的数量，每有1张就从对战对手的能量区把牌1张最多作为对象，将这些返回手牌。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MIDORIKO);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Wanna");
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.EARTH_BEAST)).get();
            if(target != null)
            {
                gainPower(target, 3000, ChronoDuration.turnEnd());
                
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex sourceCardIndex = getAbility().getSourceCardIndex();
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,sourceCardIndex.getIndexedInstance().getPower().getValue()/2)).get();
            sourceCardIndex.getIndexedInstance().banish(target);
        }

        private void onActionEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().fromEner());
            int countAdded = addToHand(data);
            if(countAdded > 0)
            {
                DataTable<CardIndex> dataOP = playerTargetCard(0,countAdded, new TargetFilter(TargetHint.HAND).OP().fromEner());
                addToHand(dataOP);
            }
        }
    }
}

package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class LRIG_B3_SouiThree extends Card {

    public LRIG_B3_SouiThree()
    {
        setImageSets("WX25-P2-022", "WX25-P2-022U");

        setOriginalName("ソウイ＝スリー");
        setAltNames("ソウイスリー Soui Surii");
        setDescription("jp",
                "@U $T1：あなたのシグニ１体がアタックしたとき、あなたの＜武勇＞のシグニ１体を対象とし、手札を１枚捨て%Bを支払ってもよい。そうした場合、ターン終了時まで、それは[[アサシン（凍結状態のシグニ）]]を得る。\n" +
                "@A $G1 @[@|ワンサイド|@]@ %B0：対戦相手は手札を裏向きで２つの束に分ける。あなたはどちらかの束を選び、対戦相手はその束を捨てる。"
        );

        setName("en", "Soui-Three");
        setDescription("en",
                "@U $T1: When 1 of your SIGNI attacks, target 1 of your <<Valor>> SIGNI, and you may discard 1 card from your hand and pay %B. If you do, until end of turn, it gains [[Assassin (frozen SIGNI)]].\n" +
                "@A $G1 @[@|One Side|@]@ %B0: Your opponent divides their hand into two face-down piles. You choose 1 of the piles, and your opponent discards it."
        );

		setName("zh_simplified", "索薇=叁");
        setDescription("zh_simplified", 
                "@U $T1 :当你的精灵1只攻击时，你的<<武勇>>精灵1只作为对象，可以把手牌1张舍弃并支付%B。这样做的场合，直到回合结束时为止，其得到[[暗杀（冻结状态的精灵）]]。\n" +
                "@A $G1 独断%B0:对战对手把手牌里向分为2份。你选其中的1份，对战对手将那份舍弃。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SOUI);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
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

            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("One Side");
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.VALOR)).get();
            
            if(target != null && payAll(new DiscardCost(1), new EnerCost(Cost.color(CardColor.BLUE, 1))))
            {
                attachAbility(target, new StockAbilityAssassin(this::onAttachedStockEffAddCond), ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexOpposite)
        {
            return cardIndexOpposite.getIndexedInstance().isState(CardStateFlag.FROZEN) ? ConditionState.OK : ConditionState.BAD;
        }

        private void onActionEff()
        {
            DataTable<CardIndex> data = playerTargetCard(getOpponent(), 0,AbilityConst.MAX_UNLIMITED, new TargetFilter().own().fromHand());
            look(data);
            
            if(playerChoiceAction(ActionHint.HAND, ActionHint.FACE_DOWN) == 1)
            {
                discard(getCardsInHand(getOpponent()));
                addToHand(getCardsInLooked(getOpponent()));
            } else {
                discard(getCardsInLooked(getOpponent()));
                addToHand(getCardsInLooked(getOpponent()));
            }
        }
    }
}

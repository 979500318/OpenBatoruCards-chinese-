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
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.stock.StockAbility;
import open.batoru.data.ability.stock.StockAbilityAssassin;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;

public final class LRIG_R3_WretchedHanayoBewitchingFlameDance extends Card {

    public LRIG_R3_WretchedHanayoBewitchingFlameDance()
    {
        setImageSets("WXDi-P12-007", "WXDi-P12-007U");

        setOriginalName("炎妖舞　花代・惨");
        setAltNames("エンヨウブハナヨサン Enyoubu Hanayo San");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるすべてのシグニが#Sの場合、あなたのシグニ１体を対象とし、%R %Xを支払ってもよい。そうした場合、ターン終了時まで、それは【アサシン】か【ダブルクラッシュ】を得る。\n" +
                "@A $T1 @[手札を１枚捨てる]@：カードを１枚引くか【エナチャージ１】をする。\n" +
                "@A @[エクシード４]@：対戦相手のライフクロス１枚をクラッシュする。"
        );

        setName("en", "Hanayo Tragic, Flame Dance");
        setDescription("en",
                "@U: At the beginning of your attack phase, if all the SIGNI on your field are #S, you may pay %R %X. If you do, target SIGNI on your field gains [[Assassin]] or [[Double Crush]] until end of turn.\n@A $T1 @[Discard a card]@: Draw a card or [[Ener Charge 1]].\n@A @[Exceed 4]@: Crush one of your opponent's Life Cloth."
        );
        
        setName("en_fan", "Wretched Hanayo, Bewitching Flame Dance");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if all of your SIGNI are #S @[Dissona]@ SIGNI, target 1 of your SIGNI, and you may pay %R %X. If you do, until end of turn, it gains [[Assassin]] or [[Double Crush]].\n" +
                "@A $T1 @[Discard 1 card from your hand]@: Draw 1 card or [[Ener Charge 1]].\n" +
                "@A @[Exceed 4]@: Crush 1 of your opponent's life cloth."
        );

		setName("zh_simplified", "炎妖舞 花代·惨");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，你的场上的全部的精灵是#S的场合，你的精灵1只作为对象，可以支付%R%X。这样做的场合，直到回合结束时为止，其得到[[暗杀]]或[[双重击溃]]。\n" +
                "@A $T1 手牌1张舍弃:抽1张牌或[[能量填充1]]。\n" +
                "@A @[超越 4]@:对战对手的生命护甲1张击溃。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HANAYO);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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

            ActionAbility act1 = registerActionAbility(new DiscardCost(1), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            registerActionAbility(new ExceedCost(4), this::onActionEff2);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getSIGNICount(getOwner()) > 0 && new TargetFilter().own().SIGNI().not(new TargetFilter().dissona()).getValidTargetsCount() == 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
                
                if(target != null && payEner(Cost.color(CardColor.RED, 1) + Cost.colorless(1)))
                {
                    StockAbility attachedStock = playerChoiceAction(ActionHint.ASSASSIN, ActionHint.DOUBLECRUSH) == 1 ? new StockAbilityAssassin() : new StockAbilityDoubleCrush();
                    attachAbility(target, attachedStock, ChronoDuration.turnEnd());
                }
            }
        }
        
        private void onActionEff1()
        {
            if(playerChoiceAction(ActionHint.DRAW, ActionHint.ENER) == 1)
            {
                draw(1);
            } else {
                enerCharge(1);
            }
        }

        private void onActionEff2()
        {
            crush(getOpponent());
        }
    }
}

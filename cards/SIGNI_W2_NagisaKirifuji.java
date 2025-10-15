package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

import java.util.OptionalDouble;

public final class SIGNI_W2_NagisaKirifuji extends Card {

    public SIGNI_W2_NagisaKirifuji()
    {
        setImageSets("WXDi-CP02-070");

        setOriginalName("桐藤ナギサ");
        setAltNames("キリフジナギサ Kirifuji Nagisa");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたの他の＜ブルアカ＞のシグニのうち最もパワーの低いシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋4000する。" +
                "~{{E @[手札から＜ブルアカ＞のカードを２枚捨てる]@：あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。@@" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。カードを１枚引く。"
        );

        setName("en", "Kirifuji Nagisa");
        setDescription("en",
                "@U: At the end of your turn, target SIGNI with the least power among other <<Blue Archive>> SIGNI on your field gets +4000 power until the end of your opponent's next end phase.~{{E @[Discard two <<Blue Archive>> cards]@: Add target SIGNI with a #G from your trash to your hand.@@" +
                "~#Target SIGNI on your opponent's field gains@>@C@#: This SIGNI cannot attack.@@@@until end of turn. Draw a card."
        );
        
        setName("en_fan", "Nagisa Kirifuji");
        setDescription("en_fan",
                "@U: At the end of your turn, target 1 of your other <<Blue Archive>> SIGNI with the lowest power, and until the end of your opponent's next turn, it gets +4000 power." +
                "~{{E @[Discard 2 <<Blue Archive>> cards from your hand]@: Target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand.@@" +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C@#: Can't attack.@@@@" +
                "Draw 1 card."
        );

		setName("zh_simplified", "桐藤渚");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，你的其他的<<ブルアカ>>精灵中力量最低的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+4000。\n" +
                "~{{E从手牌把<<ブルアカ>>牌2张舍弃从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。@@" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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

            EnterAbility enter = registerEnterAbility(new DiscardCost(2, new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)), this::onEnterEff);
            enter.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            TargetFilter filter = new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex());
            OptionalDouble minPower = filter.getExportedData().stream().mapToDouble(c -> ((CardIndex)c).getIndexedInstance().getPower().getValue()).min();
            
            if(minPower.isPresent())
            {
                CardIndex target = playerTargetCard(filter.withPower(minPower.getAsDouble())).get();
                gainPower(target, 4000, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            addToHand(target);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());

            draw(1);
        }
    }
}

package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleValueType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.cost.ExceedCost;

public final class LRIG_B3_MikomikoThreeThreeMixMix extends Card {

    public LRIG_B3_MikomikoThreeThreeMixMix()
    {
        setImageSets("WXDi-P12-008", "WXDi-P12-008U");

        setOriginalName("みこみこ☆さんさんまぜまぜ");
        setAltNames("ミコミコサンサンマゼマゼ Mikomiko San San Maze Maze");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に#Sのシグニが２体以上ある場合、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手は手札を１枚捨てる。\n" +
                "$$2対戦相手は%Xを支払ってもよい。そうしなかった場合、対戦相手は次の自分のドローフェイズの間にカードを合計１枚までしか引けない。\n" +
                "@E：対戦相手が手札を２枚捨てないかぎり、カードを２枚引く。\n" +
                "@A @[エクシード４]@：対戦相手のシグニ１体を対象とし、対戦相手が%X %X %X %Xを支払わないかぎり、それをデッキの一番下に置く。"
        );

        setName("en", "Mikomiko☆Three - Three Mix - Mix");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there are two or more #S SIGNI on your field, choose one of the following.\n" +
                "$$1 Your opponent discards a card.\n" +
                "$$2 Your opponent may pay %X. If they don't, they can only draw one card in total during their next draw phase.\n" +
                "@E: Draw two cards unless your opponent discards two cards.\n" +
                "@A @[Exceed 4]@: Put target SIGNI on your opponent's field on the bottom of its owner's deck unless they pay %X %X %X %X."
        );
        
        setName("en_fan", "Mikomiko☆Three Three Mix Mix");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are 2 or more #S @[Dissona]@ SIGNI on your field, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Your opponent discards 1 card from their hand.\n" +
                "$$2 Your opponent may pay %X. If they don't, your opponent can only draw up to 1 card in total during their next draw phase.\n" +
                "@E: Draw 2 cards unless your opponent discards 2 cards from their hand.\n" +
                "@A @[Exceed 4]@: Target 1 of your opponent's SIGNI, and put it on the bottom of their deck unless they pay %X %X %X %X."
        );

		setName("zh_simplified", "美琴琴☆三重混音");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，你的场上的#S的精灵在2只以上的场合，从以下的2种选1种。\n" +
                "$$1 对战对手把手牌1张舍弃。\n" +
                "$$2 对战对手可以支付%X。不这样做的场合，对战对手在下一个自己的抽牌阶段期间只能抽合计1张最多的牌。\n" +
                "@E :如果对战对手不把手牌2张舍弃，那么抽2张牌。\n" +
                "@A @[超越 4]@对战对手的精灵1只作为对象，如果对战对手不把%X %X %X %X:支付，那么将其放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MIKOMIKO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
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

            registerEnterAbility(this::onEnterEff);

            registerActionAbility(new ExceedCost(4), this::onActionEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().dissona().getValidTargetsCount() >= 2)
            {
                if(playerChoiceMode() == 1)
                {
                    discard(getOpponent(), 1);
                } else if(!payEner(getOpponent(), Cost.colorless(1)))
                {
                    setPlayerRuleValue(getOpponent(), PlayerRuleValueType.DRAW_PHASE_MAX, 1, ChronoDuration.nextPhaseEnd(getOpponent(), GamePhase.DRAW));
                }
            }
        }

        private void onEnterEff()
        {
            if(discard(getOpponent(), 0,2, ChoiceLogic.BOOLEAN).size() != 2)
            {
                draw(2);
            }
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();

            if(target != null && !payEner(getOpponent(), Cost.colorless(4)))
            {
                returnToDeck(target, DeckPosition.BOTTOM);
            }
        }
    }
}


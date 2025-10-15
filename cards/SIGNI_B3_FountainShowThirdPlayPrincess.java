package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_B3_FountainShowThirdPlayPrincess extends Card {

    public SIGNI_B3_FountainShowThirdPlayPrincess()
    {
        setImageSets("WX24-P3-053");

        setOriginalName("参ノ遊姫　フンスイショー");
        setAltNames("サンノユウキフンスイショー San no Yuuki Hunsui Shyou");
        setDescription("jp",
                "@U：アタックフェイズの間、このシグニが場を離れたとき、カードを１枚引き、手札からレベル２以下のシグニ１枚をダウン状態で場に出してもよい。そのシグニの@E能力は発動しない。\n" +
                "@U：このシグニがアタックしたとき、このシグニを場からデッキの一番下に置いてもよい。そうした場合、対戦相手は手札を２枚捨てる。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のシグニを２体まで対象とし、それらをダウンする。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "Fountain Show, Third Play Princess");
        setDescription("en",
                "@U: When this SIGNI leaves the field during the attack phase, draw 1 card, then you may put 1 level two or lower SIGNI from your hand onto your field downed. That SIGNI's @E abilities do not activate.\n" +
                "@U: Whenever this SIGNI attacks, you may put this SIGNI from your field on the bottom of your deck. If you do, your opponent discards two cards from their hand." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target up to 2 of your opponent's SIGNI, and down them.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "叁之游姬 喷泉秀");
        setDescription("zh_simplified", 
                "@U 攻击阶段期间，当这只精灵离场时，抽1张牌，可以从手牌把等级2以下的精灵1张以#D状态出场。那只精灵的@E能力不能发动。\n" +
                "@U :当这只精灵攻击时，可以把这只精灵从场上放置到牌组最下面。这样做的场合，对战对手把手牌2张舍弃。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的精灵2只最多作为对象，将这些#D。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        // Contributed by NebelTal
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.MOVE, this::onAutoEff1);
            auto1.setCondition(this::onAutoEffCond1);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond1()
        {
            return GamePhase.isAttackPhase(getCurrentPhase()) && !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1()
        {
            draw(1);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(0,2).fromHand().playable()).get();
            putOnField(cardIndex, Enter.DOWNED | Enter.DONT_ACTIVATE);
        }

        private void onAutoEff2()
        {
            if(getCardIndex().isSIGNIOnField() && playerChoiceActivate())
            {
                returnToDeck(getCardIndex(), DeckPosition.BOTTOM);
                
                discard(getOpponent(), 2);
            }
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
                down(data);
            } else {
                draw(1);
            }
        }
    }
}

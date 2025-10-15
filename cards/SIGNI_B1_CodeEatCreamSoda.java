package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;

public final class SIGNI_B1_CodeEatCreamSoda extends Card {

    public SIGNI_B1_CodeEatCreamSoda()
    {
        setImageSets("WXDi-P09-065");

        setOriginalName("コードイート　クリームソーダ");
        setAltNames("コードイートクリームソーダ Koodo Iito Kuriimu Sooda");
        setDescription("jp",
                "@E：対戦相手の場に凍結状態のシグニがある場合、対戦相手の手札を見る。あなたはその中から#Gを持たないカード１枚を選びデッキの一番下に置いてよい。そうした場合、対戦相手はカードを１枚引く。" +
                "~#：対戦相手のルリグかシグニ１体を対象とする。このターン、それがアタックしたとき、対戦相手が手札を３枚捨てないかぎり、そのアタックを無効にする。"
        );

        setName("en", "Cream Soda, Code: Eat");
        setDescription("en",
                "@E: If there is a frozen SIGNI on your opponent's field, look at your opponent's hand. You may choose a card without a #G from it and put that card on the bottom of your opponent's deck. If you do, your opponent draws a card." +
                "~#Whenever target LRIG or SIGNI on your opponent's field attacks this turn, negate that attack unless your opponent discards three cards."
        );
        
        setName("en_fan", "Code Eat Cream Soda");
        setDescription("en_fan",
                "@E: If there is a frozen SIGNI on your opponent's field, look at your opponent's hand. You may choose 1 card without #G @[Guard]@ from among them, and put it on the bottom of their deck. If you do, your opponent draws 1 card." +
                "~#Target 1 of your opponent's LRIG or SIGNI. This turn, whenever it attacks, disable that attack unless your opponent discards 3 cards from their hand."
        );
        
		setName("zh_simplified", "食用代号 奶油苏打");
        setDescription("zh_simplified", 
                "@E 对战对手的场上有冻结状态的精灵的场合，看对战对手的手牌。你可以从中选不持有#G的牌1张放置到牌组最下面。这样做的场合，对战对手抽1张牌。" +
                "~#对战对手的分身或精灵1只作为对象。这个回合，当其攻击时，如果对战对手不把手牌3张舍弃，那么那次攻击无效。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.COOKING);
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

            registerEnterAbility(this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            if(new TargetFilter().OP().SIGNI().withState(CardStateFlag.FROZEN).getValidTargetsCount() > 0)
            {
                reveal(getHandCount(getOpponent()), getOpponent(), CardLocation.HAND, true);
                
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.DISCARD).OP().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromRevealed()).get();
                boolean wasReturned = returnToDeck(cardIndex, DeckPosition.BOTTOM);
                
                addToHand(getCardsInRevealed(getOpponent()));
                
                if(wasReturned)
                {
                    draw(getOpponent(), 1);
                }
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().fromField()).get();

            if(target != null)
            {
                ChronoRecord record = new ChronoRecord(target, ChronoDuration.turnEnd());
                GFX.attachToChronoRecord(record, new GFXCardTextureLayer(target, new GFXTextureCardCanvas("border/discard", 0.75,3)));
                addCardRuleCheck(CardRuleCheckType.COST_TO_LAND_ATTACK, target, record, data -> new DiscardCost(0,3, ChoiceLogic.BOOLEAN));
            }
        }
    }
}

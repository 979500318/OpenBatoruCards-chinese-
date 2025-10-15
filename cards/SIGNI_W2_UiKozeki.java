package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W2_UiKozeki extends Card {

    public SIGNI_W2_UiKozeki()
    {
        setImageSets("WXDi-CP02-068");

        setOriginalName("古関ウイ");
        setAltNames("コゼキウイ Kozeki Ui");
        setDescription("jp",
                "@U $T1：あなたのターンの間、あなたの効果によって対戦相手のシグニ１体が手札に戻るかトラッシュに置かれたとき、あなたのデッキの一番上を公開する。そのカードが＜ブルアカ＞の場合、【エナチャージ１】をする。" +
                "~{{C：対戦相手のターンの間、このシグニは[[シャドウ（レベル２以下）]]を得る。"
        );

        setName("en", "Kozeki Ui");
        setDescription("en",
                "@U $T1: During your turn, when a SIGNI on your opponent's field is returned to its owner's hand or put into its owner's trash by your effect, reveal the top card of your deck. If that card is <<Blue Archive>>, [[Ener Charge 1]].~{{C: During your opponent's turn, this SIGNI gains [[Shadow -- Level two or less]]."
        );
        
        setName("en_fan", "Ui Kozeki");
        setDescription("en_fan",
                "@U $T1: During your turn, when 1 of your opponent's SIGNI is returned to the hand or put into the trash by your effect, reveal the top card of your deck. If it is a <<Blue Archive>> card, [[Ener Charge 1]]." +
                "~{{C: During your opponent's turn, this SIGNI gains [[Shadow (level 2 or lower)]]."
        );

		setName("zh_simplified", "古关忧");
        setDescription("zh_simplified", 
                "@U $T1 :你的回合期间，当因为你的效果把对战对手的精灵1只返回手牌或放置到废弃区时，你的牌组最上面公开。那张牌是<<ブルアカ>>的场合，[[能量填充1]]。\n" +
                "~{{C:对战对手的回合期间，这只精灵得到[[暗影（等级2以下）]]。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            ConstantAbility cont = registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));
            cont.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && !isOwnCard(caller) && caller.isSIGNIOnField() &&
                   (EventMove.getDataMoveLocation() == CardLocation.TRASH || EventMove.getDataMoveLocation() == CardLocation.HAND) &&
                   getEvent().getSourceAbility() != null && isOwnCard(getEvent().getSourceCardIndex()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex == null || !cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE) || enerCharge(1).get() == null)
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return (CardType.isSIGNI(cardIndexSource.getCardReference().getType()) || CardType.isLRIG(cardIndexSource.getCardReference().getType())) &&
                    cardIndexSource.getIndexedInstance().getLevel().getValue() <= 2 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}

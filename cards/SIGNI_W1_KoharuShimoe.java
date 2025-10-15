package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W1_KoharuShimoe extends Card {

    public SIGNI_W1_KoharuShimoe()
    {
        setImageSets("WXDi-CP02-063");

        setOriginalName("下江コハル");
        setAltNames("シモエコハル Shimoe Koharu");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたのデッキの一番上を公開する。その後、そのカードが＜ブルアカ＞の場合、あなたの＜ブルアカ＞のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋4000する。" +
                "~{{C：対戦相手のターンの間、このシグニは[[シャドウ（レベル２以下）]]を得る。"
        );

        setName("en", "Shimoe Koharu");
        setDescription("en",
                "@U: At the end of your turn, reveal the top card of your deck. Then, if that card is <<Blue Archive>>, target <<Blue Archive>> SIGNI on your field gets +4000 power until the end of your opponent's next end phase.~{{C: During your opponent's turn, this SIGNI gains [[Shadow -- Level two or less]]. "
        );
        
        setName("en_fan", "Koharu Shimoe");
        setDescription("en_fan",
                "@U: At the end of your turn, reveal the top card of your deck. Then, if that card is a <<Blue Archive>> card, target 1 of your <<Blue Archive>> SIGNI, and until the end of your opponent's next turn, it gets +4000 power." +
                "~{{C: During your opponent's turn, this SIGNI gains [[Shadow (level 2 or lower)]]."
        );

		setName("zh_simplified", "下江小春");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，你的牌组最上面公开。然后，那张牌是<<ブルアカ>>的场合，你的<<ブルアカ>>精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+4000。\n" +
                "~{{C:对战对手的回合期间，这只精灵得到[[暗影（等级2以下）]]。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(3000);

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

            ConstantAbility cont = registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));
            cont.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null && cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE)).get();
                gainPower(target, 4000, ChronoDuration.nextTurnEnd(getOpponent()));
            }
            
            returnToDeck(cardIndex, DeckPosition.TOP);
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

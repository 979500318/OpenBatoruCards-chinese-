package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.CardDataColor;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.modifiers.ModifiableBaseValueModifier;

import java.util.Collections;

public final class SIGNI_WRG1_CodeArtLMic extends Card {

    public SIGNI_WRG1_CodeArtLMic()
    {
        setImageSets("WXDi-P16-086");

        setOriginalName("コードアート　Ｌ・マイク");
        setAltNames("コードアートエルマイク Koodo Aato Eru Maiku L-Mic");
        setDescription("jp",
                "=T ＜Card Jockey＞\n" +
                "^E @[手札を１枚捨てる]@：あなたのデッキの上からカードを４枚見る。その中からカードを１枚まで手札に加えるかエナゾーンに置き、好きな枚数のカードを好きな順番でデッキの一番下に置き、残りを好きな順番でデッキの一番上に戻す。\n\n" +
                "@C：あなたの場に＜Card Jockey＞のルリグが３体いないかぎり、このカードはすべての領域で色を失う。"
        );

        setName("en", "L - Mic, Code: Art");
        setDescription("en",
                "=T <<Card Jockey>>\n^E Discard a card: Look at the top four cards of your deck. Add up to one card from among them to your hand or put it into your Ener Zone, put any number of the remaining cards on the bottom of your deck in any order and the rest on top of your deck in any order.\n\n@C: This card loses its colors in all zones unless there are three <<Card Jockey>> LRIG on your field."
        );
        
        setName("en_fan", "Code Art L Mic");
        setDescription("en_fan",
                "=T <<Card Jockey>>\n" +
                "^E @[Discard 1 card from your hand]@: Look at the top 4 cards of your deck. Add up to 1 card from among them to your hand or put it into the ener zone, put any number from among them to the bottom of your deck, and put the rest on the top of your deck in any order.\n\n" +
                "@C: If there aren't 3 <<Card Jockey>> LRIG on your field, this SIGNI loses all of its colors in all zones."
        );

		setName("zh_simplified", "必杀代号 L·麦克");
        setDescription("zh_simplified", 
                "=T<<Card:Jockey>>\n" +
                "^E手牌1张舍弃:从你的牌组上面看4张牌。从中把牌1张最多加入手牌或放置到能量区，任意张数的牌任意顺序放置到牌组最下面，剩下的任意顺序返回牌组最上面。\n" +
                "@C 你的场上的<<Card:Jockey>>分身没有在3只时，这张牌在全部的领域的颜色失去。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.RED, CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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

            EnterAbility enter = registerEnterAbility(new DiscardCost(1), this::onEnterEff);
            enter.setCondition(this::onEnterEffCond);
            
            ConstantAbility cont = registerConstantAbility(this::onConstEffCond, new ModifiableBaseValueModifier<>(this::onConstEffModGetSample, () -> CardDataColor.EMPTY_VALUE));
            cont.getFlags().addValue(AbilityFlag.IGNORE_LOCATION | AbilityFlag.IGNORE_UNDER_FLAGS);
        }
        
        private ConditionState onEnterEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.CARD_JOCKEY) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onEnterEff()
        {
            look(4);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter().own().fromLooked()).get();
            if(cardIndex != null)
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.ENER) == 1)
                {
                    addToHand(cardIndex);
                } else {
                    putInEner(cardIndex);
                }
            }

            DataTable<CardIndex> data = playerTargetCard(0,AbilityConst.MAX_UNLIMITED, new TargetFilter(TargetHint.BOTTOM).own().fromLooked());
            returnToDeck(data, DeckPosition.BOTTOM);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
        
        private ConditionState onConstEffCond()
        {
            return !isLRIGTeam(CardLRIGTeam.CARD_JOCKEY) ? ConditionState.OK : ConditionState.BAD;
        }
        private CardDataColor onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getColor();
        }
    }
}

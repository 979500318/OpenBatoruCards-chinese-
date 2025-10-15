package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_K2_BrutusWickedGeneral extends Card {
    
    public SIGNI_K2_BrutusWickedGeneral()
    {
        setImageSets("WXDi-P04-082");
        
        setOriginalName("凶将　ブルータス");
        setAltNames("キョウショウブルータス Kyoushou Buruutasu");
        setDescription("jp",
                "@C：このシグニは中央のシグニゾーンにあるかぎり、@>@U：このシグニがアタックしたとき、あなたか対戦相手のデッキの上からカードを４枚トラッシュに置く。@@を得る。"
        );
        
        setName("en", "Brutus, Doomed General");
        setDescription("en",
                "@C: As long as this SIGNI is in your center SIGNI Zone, it gains@>@U: Whenever this SIGNI attacks, put the top four cards of your deck or your opponent's deck into their owner's trash."
        );
        
        setName("en_fan", "Brutus, Wicked General");
        setDescription("en_fan",
                "@C: As long as this SIGNI is in your center SIGNI zone, it gains:" +
                "@>@U: Whenever this SIGNI attacks, put the top 4 cards of your or your opponent's deck into the trash."
        );
        
		setName("zh_simplified", "凶将 布鲁图斯");
        setDescription("zh_simplified", 
                "@C :这只精灵在中央的精灵区时，得到\n" +
                "@>@U :当这只精灵攻击时，从你或对战对手的牌组上面把4张牌放置到废弃区。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));
        }
        
        private ConditionState onConstEffCond()
        {
            return getCardIndex().getLocation() == CardLocation.SIGNI_CENTER ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
        }
        private void onAttachedAutoEff()
        {
            millDeck(playerChoiceAction(ActionHint.OWN, ActionHint.OPPONENT) == 1 ? getOwner() : getOpponent(), 4);
        }
    }
}

package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K1_BaphometWickedDevil extends Card {
    
    public SIGNI_K1_BaphometWickedDevil()
    {
        setImageSets("WXDi-D07-014", "SPDi10-04");
        
        setOriginalName("凶魔　バフォメット");
        setAltNames("キョウマバフォメット Kyouma Bafometto");
        setDescription("jp",
                "@C：あなたのトラッシュにカードが５枚以上あるかぎり、このシグニのパワーは＋4000される。\n" +
                "@E：あなたのデッキの上からカードを３枚トラッシュに置く。"
        );
        
        setName("en", "Baphomet, Doomed Evil");
        setDescription("en",
                "@C: As long as you have five or more cards in your trash, this SIGNI gets +4000 power.\n" +
                "@E: Put the top three cards of your deck into your trash."
        );
        
        setName("en_fan", "Baphomet, Wicked Devil");
        setDescription("en_fan",
                "@C: As long as there are 5 or more cards in your trash, this SIGNI gets +4000 power.\n" +
                "@E: Put the top 3 cards of your deck into the trash."
        );
        
		setName("zh_simplified", "凶魔 巴风特");
        setDescription("zh_simplified", 
                "@C :你的废弃区的牌在5张以上时，这只精灵的力量+4000。\n" +
                "@E :从你的牌组上面把3张牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return getTrashCount(getOwner()) >= 5 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            millDeck(3);
        }
    }
}

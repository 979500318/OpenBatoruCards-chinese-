package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_W1_LemonTetraWaterPhantom extends Card {
    
    public SIGNI_W1_LemonTetraWaterPhantom()
    {
        setImageSets("WXDi-P04-046");
        
        setOriginalName("幻水　レモンテトラ");
        setAltNames("ゲンスイレモンテトラ Gensui Remon Tetora");
        setDescription("jp",
                "@U：あなたが[[ガード]]したとき、カードを１枚引く。"
        );
        
        setName("en", "Lemon Tetra, Phantom Aquatic Beast");
        setDescription("en",
                "@U: Whenever you [[Guard]], draw a card."
        );
        
        setName("en_fan", "Lemon Tetra, Water Phantom");
        setDescription("en_fan",
                "@U: Whenever you [[Guard]], draw 1 card."
        );
        
		setName("zh_simplified", "幻水 柠檬灯鱼");
        setDescription("zh_simplified", 
                "@U :当你[[防御]]时，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.GUARD, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            draw(1);
        }
    }
}

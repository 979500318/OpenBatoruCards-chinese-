package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B2_TeamPaletteAzureBeauty extends Card {
    
    public SIGNI_B2_TeamPaletteAzureBeauty()
    {
        setImageSets("WXDi-P04-067");
        
        setOriginalName("蒼美　パレット団");
        setAltNames("ソウビパレットダン Soubi Parette Dan");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、各プレイヤーはカードを１枚引く。"
        );
        
        setName("en", "Team Palette, Azure Beauty");
        setDescription("en",
                "@U: At the beginning of your attack phase, each player draws a card."
        );
        
        setName("en_fan", "Team Palette, Azure Beauty");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, each player draws 1 card."
        );
        
		setName("zh_simplified", "苍美 调色团");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，各玩家抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
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
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            draw(1);
            draw(getOpponent(), 1);
        }
    }
}

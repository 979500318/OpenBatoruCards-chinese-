package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B1_SanielAzureAngel extends Card {
    
    public SIGNI_B1_SanielAzureAngel()
    {
        setImageSets("WXDi-P06-060");
        
        setOriginalName("蒼天　サニエル");
        setAltNames("ソウテンサニエル Souten Sanieru");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたのトラッシュに＜天使＞のシグニが１０枚以上ある場合、カードを１枚引く。"
        );
        
        setName("en", "Saniel, Azure Angel");
        setDescription("en",
                "@U: At the end of your turn, if there are ten or more <<Angel>> SIGNI in your trash, draw a card."
        );
        
        setName("en_fan", "Saniel, Azure Angel");
        setDescription("en_fan",
                "@U: At the end of your turn, if there are 10 or more <<Angel>> SIGNI in your trash, draw 1 card."
        );
        
		setName("zh_simplified", "苍天 萨尼耶尔");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，你的废弃区的<<天使>>精灵在10张以上的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(2000);
        
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
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromTrash().getValidTargetsCount() >= 10)
            {
                draw(1);
            }
        }
    }
}

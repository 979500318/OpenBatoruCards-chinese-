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

public final class SIGNI_B2_HauyniteNaturalStone extends Card {
    
    public SIGNI_B2_HauyniteNaturalStone()
    {
        setImageSets("WXDi-P05-064");
        
        setOriginalName("羅石　アウイナイト");
        setAltNames("ラセキアウイナイト Raseki Auinaito");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に青のシグニが３種類以上ある場合、カードを１枚引く。"
        );
        
        setName("en", "Hauynite, Natural Crystal");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there are three or more different blue SIGNI on your field, draw a card."
        );
        
        setName("en_fan", "Hauynite, Natural Stone");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are 3 or more different blue SIGNI on your field, draw 1 card."
        );
        
		setName("zh_simplified", "罗石 蓝方石");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的蓝色的精灵在3种类以上的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withColor(CardColor.BLUE).
                getExportedData().stream().map(c -> ((CardIndex)c).getCardReference().getOriginalName()).distinct().count() >= 3)
            {
                draw(1);
            }
        }
    }
}

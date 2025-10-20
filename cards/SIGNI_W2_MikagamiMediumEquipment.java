package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_W2_MikagamiMediumEquipment extends Card {
    
    public SIGNI_W2_MikagamiMediumEquipment()
    {
        setImageSets("WXDi-P06-049");
        
        setOriginalName("中装　ミカガミ");
        setAltNames("チュウソウミカガミ Chuusou Mikagami");
        setDescription("jp",
                "@U：あなたのルリグ１体がアタックしたとき、アップ状態のこのシグニをダウンしてもよい。そうした場合、カードを１枚引く。"
        );
        
        setName("en", "Mikagami, High Armed");
        setDescription("en",
                "@U: Whenever a LRIG on your field attacks, you may down this upped SIGNI. If you do, draw a card."
        );
        
        setName("en_fan", "Mikagami, Medium Equipment");
        setDescription("en_fan",
                "@U: Whenever your LRIG attacks, you may down this upped SIGNI. If you do, draw 1 card."
        );
        
		setName("zh_simplified", "中装 御镜");
        setDescription("zh_simplified", 
                "@U :当你的分身1只攻击时，可以把竖直状态的这只精灵横置。这样做的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && CardType.isLRIG(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(!isState(CardStateFlag.DOWNED) && playerChoiceActivate() && down())
            {
                draw(1);
            }
        }
    }
}

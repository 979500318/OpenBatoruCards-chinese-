package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R1_ErisCrimsonAngel extends Card {
    
    public SIGNI_R1_ErisCrimsonAngel()
    {
        setImageSets("WXDi-P00-049");
        
        setOriginalName("紅天　エリース");
        setAltNames("コウテンエリース Kooten Eriisu");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの赤のシグニ１体を対象とし、ターン終了時まで、それのパワーを＋2000する。" +
                "~#：カードを１枚引き、[[エナチャージ１]]をする。"
        );
        
        setName("en", "Eyris, Crimson Angel");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, target red SIGNI on your field gets +2000 power until end of turn." +
                "~#Draw a card and [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Eris, Crimson Angel");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, target 1 of your red SIGNI, and until end of turn, that SIGNI gets +2000 power." +
                "~#Draw 1 card, and [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "红天 厄里斯");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的红色的精灵1只作为对象，直到回合结束时为止，其的力量+2000。" +
                "~#抽1张牌，[[能量填充1]]。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withColor(CardColor.RED)).get();
            gainPower(cardIndex, 2000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            draw(1);
            enerCharge(1);
        }
    }
}

package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_G1_ParajulisWaterPhantom extends Card {
    
    public SIGNI_G1_ParajulisWaterPhantom()
    {
        setImageSets("WXDi-P02-074", "SPDi01-55");
        
        setOriginalName("幻水　キュウセン");
        setAltNames("ゲンスイキュウセン Gensui Kyuusen");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このターンにあなたのデッキならカードが１枚以上エナゾーンに移動していた場合、対戦相手のパワー8000以下のシグニ１体を対象とし、%G %Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Parajulis, Phantom Aquatic Beast");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if one or more cards were moved from your deck to your Ener Zone this turn, you may pay %G %X. If you do, vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Parajulis, Water Phantom");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if 1 or more cards were moved from your deck to the ener zone this turn, target 1 of your opponent's SIGNI with power 8000 or less, and you may pay %G %X. If you do, banish it."
        );
        
		setName("zh_simplified", "幻水 大鳞海猪鱼");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这个回合从你的牌组把牌1张以上往能量区移动过的场合，对战对手的力量8000以下的精灵1只作为对象，可以支付%G%X。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
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
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }
        
        private void onAutoEff()
        {
            if(GameLog.getTurnRecordsCount(event ->
                event.getId() == GameEventId.ENER && isOwnCard(event.getCaller()) &&
                event.getCaller().isEffectivelyAtLocation(CardLocation.DECK_MAIN)) > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
                
                if(target != null && payEner(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)))
                {
                    banish(target);
                }
            }
        }
    }
}

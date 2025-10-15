package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_G3_OsagitsunePhantomBeastDeity extends Card {
    
    public SIGNI_G3_OsagitsunePhantomBeastDeity()
    {
        setImageSets("WXDi-P01-042", "SPDi10-03");
        
        setOriginalName("幻獣神　オサギツネ");
        setAltNames("ゲンジュウシンオサギツネ Genjuushin Osagitsune");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このターンにあなたのデッキからカードが１枚以上エナゾーンに移動していた場合、対戦相手のパワー12000以上のシグニ１体を対象とし、%G %Xを支払ってもよい。そうした場合、それをバニッシュする。\n" +
                "@E：他のシグニを２体まで対象とし、ターン終了時まで、それらのパワーを＋5000する。"
        );
        
        setName("en", "Osagitsune, Phantom Terra Beast God");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if you moved one or more cards from your deck into your Ener Zone this turn, you may pay %G %X. If you do, vanish target SIGNI on your opponent's field with power 12000 or more.\n" +
                "@E: Up to two other target SIGNI get +5000 power until end of turn."
        );
        
        setName("en_fan", "Osagitsune, Phantom Beast Deity");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if 1 or more cards were moved from your deck to the the ener zone during this turn, target 1 of your opponent's SIGNI with power 12000 or more, and you may pay %G %X. If you do, banish it.\n" +
                "@E: Target up to 2 other SIGNI, and until end of turn, they get +5000 power."
        );
        
		setName("zh_simplified", "幻兽神 御先狐狐");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这个回合从你的牌组把牌1张以上往能量区移动过的场合，对战对手的力量12000以上的精灵1只作为对象，可以支付%G%X。这样做的场合，将其破坏。\n" +
                "@E :其他的精灵2只最多作为对象，直到回合结束时为止，这些的力量+5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onAutoEff()
        {
            if(GameLog.getTurnRecordsCount(event ->
                event.getId() == GameEventId.ENER && isOwnCard(event.getCaller()) &&
                event.getCaller().isEffectivelyAtLocation(CardLocation.DECK_MAIN)) > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(12000,0)).get();
                
                if(target != null && payEner(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)))
                {
                    banish(target);
                }
            }
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.PLUS).SIGNI().except(getCardIndex()));
            gainPower(data, 5000, ChronoDuration.turnEnd());
        }
    }
}

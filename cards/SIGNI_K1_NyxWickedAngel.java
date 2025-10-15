package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K1_NyxWickedAngel extends Card {
    
    public SIGNI_K1_NyxWickedAngel()
    {
        setImageSets("WXDi-P02-081");
        
        setOriginalName("凶天　ニュクス");
        setAltNames("キョウテンニュクス Kyouten Nyukusu");
        setDescription("jp",
                "@E：あなたの場に＜天使＞のシグニが３体以上ある場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。"
        );
        
        setName("en", "Nyx, Doomed Angel");
        setDescription("en",
                "@E: If there are three or more <<Angel>> SIGNI on your field, target SIGNI on your opponent's field gets --2000 power until end of turn."
        );
        
        setName("en_fan", "Nyx, Wicked Angel");
        setDescription("en_fan",
                "@E: If there are 3 or more <<Angel>> SIGNI on your field, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power."
        );
        
		setName("zh_simplified", "凶天 倪克斯");
        setDescription("zh_simplified", 
                "@E :你的场上的<<天使>>精灵在3只以上的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ANGEL).getValidTargetsCount() >= 3)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -2000, ChronoDuration.turnEnd());
            }
        }
    }
}

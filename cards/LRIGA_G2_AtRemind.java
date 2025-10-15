package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataSIGNIClass;
import open.batoru.data.ability.CardAbilities;
public final class LRIGA_G2_AtRemind extends Card {
    
    public LRIGA_G2_AtRemind()
    {
        setImageSets("WXDi-P00-021");
        
        setOriginalName("アト＝リマインド");
        setAltNames("アトリマインド Ato Rimaindo");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E：あなたの場にあるいずれかのシグニと共通するクラスを持つ対戦相手のシグニ１体を対象とし、それをエナゾーンに置く。そうした場合、[[エナチャージ１]]をする。\n"
        );
        
        setName("en", "At =Reminder=");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field.\n" +
                "@E: Put target SIGNI on your opponent's field that shares a class with a SIGNI on your field into its owner's Ener Zone. If you do, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "At-Remind");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and banish it.\n" +
                "@E: Target 1 of your opponent's SIGNI that shares a common class with a SIGNI on your field, and put it into the ener zone. If you do, [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "亚特=回想");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其破坏。\n" +
                "@E :与你的场上任一只的精灵持有共通类别的对战对手的精灵1只作为对象，将其放置到能量区。这样做的场合，[[能量填充1]]。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AT);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.GREEN);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(cardIndex);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).OP().SIGNI().withClass(new CardDataSIGNIClass(CardAbilities.getSIGNIClasses(getSIGNIOnField(getOwner()))))).get();
            
            if(target != null && putInEner(target))
            {
                enerCharge(1);
            }
        }
    }
}

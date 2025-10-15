package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

import java.util.List;

public final class SIGNI_G3_EinsteinVerdantWisdomPrincess extends Card {
    
    public SIGNI_G3_EinsteinVerdantWisdomPrincess()
    {
        setImageSets("WXDi-P08-045");
        
        setOriginalName("翠英姫　アインシュタイン");
        setAltNames("スイエイキアインシュタイン Suieiki Ainshutain");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるシグニのレベルの合計が偶数の場合、【エナチャージ１】をする。\n" +
                "@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、あなたのエナゾーンからレベルの合計が８になるようにシグニを好きな枚数トラッシュに置いてもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Einstein, Jade Wisdom Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, if the total level of SIGNI on your field is even, [[Ener Charge 1]].\n" +
                "@U: Whenever this SIGNI attacks, you may put any number of SIGNI whose total level is equal to eight from your Ener Zone into your trash. If you do, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Einstein, Verdant Wisdom Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if the total level of SIGNI on your field is even, [[Ener Charge 1]].\n" +
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and you may put any number of SIGNI with total level of 8 from your ener zone into the trash. If you do, banish it."
        );
        
		setName("zh_simplified", "翠英姬 爱因斯坦");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的精灵的等级的合计在偶数的场合，[[能量填充1]]。\n" +
                "@U :当这只精灵攻击时，对战对手的精灵1只作为对象，可以从你的能量区把等级的合计在8的精灵任意张数放置到废弃区。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WISDOM);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().getExportedData().stream().mapToInt(c -> ((CardIndex)c).getIndexedInstance().getLevel().getValue()).sum() % 2 == 0)
            {
                enerCharge(1);
            }
        }
        
        private void onAutoEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null)
            {
                DataTable<CardIndex> data = playerTargetCard(0,AbilityConst.MAX_UNLIMITED, new TargetFilter(TargetHint.TRASH).own().SIGNI().fromEner(), this::onAutoEff2TargetCond);
                
                if(data.get() != null && trash(data) == data.size())
                {
                    banish(target);
                }
            }
        }
        private boolean onAutoEff2TargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.isEmpty() || listPickedCards.stream().mapToInt(c -> c.getIndexedInstance().getLevel().getValue()).sum() == 8;
        }
    }
}

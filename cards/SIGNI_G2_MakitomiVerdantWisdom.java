package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_G2_MakitomiVerdantWisdom extends Card {

    public SIGNI_G2_MakitomiVerdantWisdom()
    {
        setImageSets("WXDi-P15-095");

        setOriginalName("翠英　マキトミ");
        setAltNames("スイエイマキトミ Suiei Makitomi");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、それのレベル１につきあなたのエナゾーンからそれと同じレベルの緑のシグニ１枚をトラッシュに置いてもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Makitomi, Jade Mind");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may put a green SIGNI from your Ener Zone with the same level as target SIGNI on your opponent's field into your trash for each level of the targeted SIGNI. If you do, vanish it. "
        );
        
        setName("en_fan", "Makitomi, Verdant Wisdom");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and you may put a number of green SIGNI with the same level as that SIGNI from your ener zone into the trash equal to that SIGNI's level. If you do, banish it."
        );

		setName("zh_simplified", "翠英 牧野富太郎");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，可以依据其的等级的数量，每有1级就从你的能量区把与其相同等级的绿色的精灵1张放置到废弃区。这样做的场合，将其破坏。（例如，等级3的精灵作为对象的场合，等级3的绿色的精灵3张放置到废弃区）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WISDOM);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null)
            {
                int level = target.getIndexedInstance().getLevel().getValue();
                
                DataTable<CardIndex> data = playerTargetCard(0,level, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().SIGNI().withColor(CardColor.GREEN).withLevel(level).fromEner());
                if(trash(data) == level)
                {
                    banish(target);
                }
            }
        }
    }
}

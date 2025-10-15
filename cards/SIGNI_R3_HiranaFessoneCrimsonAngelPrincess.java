package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataColor;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

import java.util.HashSet;
import java.util.Set;

public final class SIGNI_R3_HiranaFessoneCrimsonAngelPrincess extends Card {

    public SIGNI_R3_HiranaFessoneCrimsonAngelPrincess()
    {
        setImageSets("WXDi-P14-042", "WXDi-P14-042P");

        setOriginalName("紅天姫　ヒラナ//フェゾーネ");
        setAltNames("コウテンキヒラナフェゾーネ Koutenki Hirana Fezoone");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのエナゾーンにあるすべてのカードをトラッシュに置いてもよい。この方法でカードを７枚以上トラッシュに置いた場合、対戦相手のライフクロス１枚をクラッシュする。\n" +
                "@U：このシグニがアタックしたとき、あなたの場に共通する色を持つルリグが２体以上いる場合、対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Hirana//Fesonne, Crimson Angel Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may put all cards in your Ener Zone into your trash. If seven or more cards were put into your trash this way, crush one of your opponent's Life Cloth.\n@U: Whenever this SIGNI attacks, if there are two or more LRIG that share a color on your field, vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Hirana//Fessone, Crimson Angel Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, you may put all cards from your ener zone into the trash. If you put 7 or more cards into the trash this way, crush 1 of your opponent's life cloth.\n" +
                "@U: Whenever this SIGNI attacks, if there are 2 or more LRIG that share a common color on your field, target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );

		setName("zh_simplified", "红天姬 平和//音乐节");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，可以把你的能量区的全部的牌放置到废弃区。这个方法把牌7张以上放置到废弃区的场合，对战对手的生命护甲1张击溃。\n" +
                "@U :当这只精灵攻击时，你的场上的持有共通颜色的分身在2只以上的场合，对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            if(playerChoiceActivate() && trash(getCardsInEner(getOwner())) >= 7)
            {
                crush(getOpponent());
            }
        }
        
        private void onAutoEff2()
        {
            DataTable<CardIndex> data = getLRIGs(getOwner());
            if(data.size() < 2) return;

            Set<CardColor> cacheColors = new HashSet<>();
            for(int i=0;i<data.size();i++)
            {
                CardDataColor color = data.get(i).getIndexedInstance().getColor();
                if(color.matches(cacheColors))
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
                    banish(target);
                    
                    break;
                }
                
                cacheColors.addAll(color.getValue());
            }
        }
    }
}

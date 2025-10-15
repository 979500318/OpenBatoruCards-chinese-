package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.CardDataColor;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.modifiers.ModifiableBaseValueModifier;

public final class SIGNI_RWB1_HMicNaturalStone extends Card {

    public SIGNI_RWB1_HMicNaturalStone()
    {
        setImageSets("WXDi-P16-087");

        setOriginalName("羅石　Ｈ・マイク");
        setAltNames("ラセキエイチマイク Raseki Eichi Maiku H-Mic");
        setDescription("jp",
                "=T ＜Ｎｏ　Ｌｉｍｉｔ＞\n" +
                "^E：カードを２枚引き、手札を２枚捨てる。その後、この方法で捨てたシグニのレベルの合計が３以下の場合、次の対戦相手のターン終了時まで、このシグニのパワーを＋5000する。レベルの合計が４以上の場合、対戦相手のパワー2000以下のシグニ１体を対象とし、それをバニッシュする。\n\n" +
                "@C：あなたの場に＜Ｎｏ　Ｌｉｍｉｔ＞のルリグが３体いないかぎり、このカードはすべての領域で色を失う。"
        );

        setName("en", "H - Mic, Natural Crystal");
        setDescription("en",
                "=T <<No Limit>>\n^E: Draw two cards and discard two cards. Then, if the total level of SIGNI discarded this way is three or less, this SIGNI gets +5000 power until the end of your opponent's next end phase. If the total level is four or more, vanish target SIGNI on your opponent's field with power 2000 or less.\n\n@C: This card loses its colors in all zones unless there are three <<No Limit>> LRIG on your field."
        );
        
        setName("en_fan", "H Mic Natural Stone");
        setDescription("en_fan",
                "=T <<No Limit>>\n" +
                "^E: Draw 2 cards, and discard 2 cards from your hand. Then, if the total level of the SIGNI discarded this way is 3 or lower, until the end of your opponent's next turn, this SIGNI gets +5000 power. If the total level is 4 or higher, target 1 of your opponent's SIGNI with power 2000 or less, and banish it.\n\n" +
                "@C: If there aren't 3 <<No Limit>> LRIG on your field, this SIGNI loses all of its colors in all zones."
        );

		setName("zh_simplified", "罗石 H·麦克");
        setDescription("zh_simplified", 
                "=T<<No:Limit>>\n" +
                "^E:抽2张牌，手牌2张舍弃。然后，这个方法舍弃的精灵的等级的合计在3以下的场合，直到下一个对战对手的回合结束时为止，这只精灵的力量+5000。等级的合计在4以上的场合，对战对手的力量2000以下的精灵1只作为对象，将其破坏。\n" +
                "@C 你的场上的<<No:Limit>>分身没有在3只时，这张牌在全部的领域的颜色失去。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED, CardColor.WHITE, CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
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

            EnterAbility enter = registerEnterAbility(this::onEnterEff);
            enter.setCondition(this::onEnterEffCond);

            ConstantAbility cont = registerConstantAbility(this::onConstEffCond, new ModifiableBaseValueModifier<>(this::onConstEffModGetSample, () -> CardDataColor.EMPTY_VALUE));
            cont.getFlags().addValue(AbilityFlag.IGNORE_LOCATION | AbilityFlag.IGNORE_UNDER_FLAGS);
        }

        private ConditionState onEnterEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.NO_LIMIT) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onEnterEff()
        {
            draw(2);
            DataTable<CardIndex> data = discard(2);
            
            if(data.get() != null)
            {
                int sum = 0;
                for(int i=0;i<data.size();i++)
                {
                    if(!CardType.isSIGNI(data.get(i).getIndexedInstance().getTypeByRef())) continue;
                    sum += data.get(i).getIndexedInstance().getLevelByRef();
                }
                
                if(sum <= 3)
                {
                    gainPower(getCardIndex(), 5000, ChronoDuration.nextTurnEnd(getOpponent()));
                } else {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,2000)).get();
                    banish(target);
                }
            }
        }

        private ConditionState onConstEffCond()
        {
            return !isLRIGTeam(CardLRIGTeam.NO_LIMIT) ? ConditionState.OK : ConditionState.BAD;
        }
        private CardDataColor onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getColor();
        }
    }
}

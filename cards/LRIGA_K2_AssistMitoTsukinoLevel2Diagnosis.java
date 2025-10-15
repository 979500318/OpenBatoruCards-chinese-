package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.Enter;

public final class LRIGA_K2_AssistMitoTsukinoLevel2Diagnosis extends Card {

    public LRIGA_K2_AssistMitoTsukinoLevel2Diagnosis()
    {
        setImageSets("WXDi-CP01-024");

        setOriginalName("【アシスト】月ノ美兎　レベル２【診断】");
        setAltNames("アシストツキノミトレベルニシンダン Ashisuto Tsukino Mito Reberu Ni Shindan Assist Mito Assist Tsukino");
        setDescription("jp",
                "@E：以下を３回行う。「あなたのトラッシュから対戦相手の場にあるシグニ１体と同じパワーの＜バーチャル＞のシグニを１枚まで対象とし、それをその対戦相手のシグニの正面のシグニゾーンに出す。それの@E能力は発動しない。」"
        );

        setName("en", "[Assist] Mito, Level 2 [Diagnosis]");
        setDescription("en",
                "@E: Perform the following three times. \"Put up to one target <<Virtual>> SIGNI with the same power as a SIGNI on your opponent's field from your trash into the SIGNI Zone in front of that opponent's SIGNI. The @E abilities of SIGNI put onto your field this way do not activate.\""
        );
        
        setName("en_fan", "[Assist] Mito Tsukino Level 2 [Diagnosis]");
        setDescription("en_fan",
                "@E: Do the following 3 times: \"Target up to 1 <<Virtual>> SIGNI from your trash with the same power as 1 of your opponent's SIGNI, and put it into the SIGNI zone in front of that opponent's SIGNI. Its @E abilities don't activate.\""
        );

		setName("zh_simplified", "【支援】月之美兔 等级2【诊断】");
        setDescription("zh_simplified", 
                "@E :进行以下3次。\n" +
                "@>从你的废弃区把与对战对手的场上的精灵1只相同力量的<<バーチャル>>精灵1张最多作为对象，将其在那只对战对手的精灵的正面的精灵区出场。其的@E能力不能发动。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MITO);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(3));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

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
            for(int i=0;i<3;i++)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter().OP().SIGNI(), false).get();
                if(cardIndex != null)
                {
                    CardIndex target = playerTargetCard(0, 1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).withPower(cardIndex.getIndexedInstance().getPower().getValue())).get();
                    putOnField(target, CardLocation.getOppositeSIGNILocation(cardIndex.getLocation()), Enter.DONT_ACTIVATE);
                }
            }
        }
    }
}

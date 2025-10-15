package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_W2_AssistAngeLevel2Dash extends Card {
    
    public LRIGA_W2_AssistAngeLevel2Dash()
    {
        setImageSets("WXDi-P00-028");
        
        setOriginalName("【アシスト】アンジュ　レベル２’");
        setAltNames("アシストアンジュレベルニダッシュ Ashisuto Anju Reberu Ni Dasshu Dash Assist Ange");
        setDescription("jp",
                "@E：あなたの手札から＜バーチャル＞のシグニ１枚を場に出す。それの@E能力は発動しない。\n" +
                "@E %X %X：あなたの手札から＜バーチャル＞のシグニ１枚を場に出す。それの@E能力は発動しない。"
        );
        
        setName("en", "[Assist] Ange, Level 2'");
        setDescription("en",
                "@E: Put a <<Virtual>> SIGNI from your hand onto your field. The @E abilities of SIGNI put onto your field this way do not activate.\n" +
                "@E %X %X: Put a <<Virtual>> SIGNI from your hand onto the field. The @E abilities of SIGNI put onto your field this way do not activate."
        );
        
        setName("en_fan", "[Assist] Ange Level 2'");
        setDescription("en_fan",
                "@E: Put 1 <<Virtual>> SIGNI from your hand onto the field. It's @E abilities don't activate.\n" +
                "@E %X %X: Put 1 <<Virtual>> SIGNI from your hand onto the field. It's @E abilities don't activate."
        );
        
		setName("zh_simplified", "【支援】安洁 等级2'");
        setDescription("zh_simplified", 
                "@E 从你的手牌把<<バーチャル>>精灵1张出场。其的@E能力不能发动。\n" +
                "@E %X %X从你的手牌把<<バーチャル>>精灵1张出场。其的@E能力不能发动。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ANGE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.WHITE);
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
            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromHand().playable()).get();
            putOnField(target, Enter.DONT_ACTIVATE);
        }
    }
}

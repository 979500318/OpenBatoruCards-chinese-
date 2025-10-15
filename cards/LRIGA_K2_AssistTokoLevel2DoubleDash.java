package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIGA_K2_AssistTokoLevel2DoubleDash extends Card {
    
    public LRIGA_K2_AssistTokoLevel2DoubleDash()
    {
        setImageSets("WXDi-P00-032");
        
        setOriginalName("【アシスト】とこ　レベル２’’");
        setAltNames("アシストとこレベルニダブルダッシュ Ashisuto Toko Reberu Ni Daburu Dasshu Double Dash Assist Toko");
        setDescription("jp",
                "@E：あなたのトラッシュから＜バーチャル＞のシグニ１枚を対象とし、それを場に出す。"
        );
        
        setName("en", "[Assist] Toko, Level 2''");
        setDescription("en",
                "@E: Put target <<Virtual>> SIGNI from your trash onto your field."
        );
        
        setName("en_fan", "[Assist] Toko Level 2''");
        setDescription("en_fan",
                "@E: Target 1 <<Virtual>> SIGNI from your trash, and put it onto the field."
        );
        
		setName("zh_simplified", "【支援】床 等级2''");
        setDescription("zh_simplified", 
                "@E :从你的废弃区把<<バーチャル>>精灵1张作为对象，将其出场。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.TOKO);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(4));
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).playable().fromTrash()).get();
            putOnField(target);
        }
    }
}
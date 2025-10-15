package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W3_Code2434KaedeHiguchi extends Card {
    
    public SIGNI_W3_Code2434KaedeHiguchi()
    {
        setImageSets("WXDi-D02-24");
        
        setOriginalName("コード２４３４　樋口楓");
        setAltNames("コードニジサンジヒグチカエデ Koodo Nijisanji Higuchi Kaede");
        setDescription("jp",
                "@C：あなたの中央のシグニゾーンにある＜バーチャル＞のシグニのシグニのパワーを＋3000する。\n" +
                "@U：このシグニがバニッシュされたとき、ターン終了時まで、あなたのすべての＜バーチャル＞のシグニのパワーを＋3000する。" +
                "~#：あなたのトラッシュから＜バーチャル＞のシグニを２枚まで対象とし、それらを手札に加える。"
        );
        
        setName("en", "Kaede Higuchi, Code 2434");
        setDescription("en",
                "@C: <<Virtual>> SIGNI in your center SIGNI Zone get +3000 power.\n" +
                "@U: When this SIGNI is vanished, all <<Virtual>> SIGNI on your field get +3000 power until end of turn." +
                "~#Add up to two target <<Virtual>> SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Code 2434 Kaede Higuchi");
        setDescription("en_fan",
                "@C: The <<Virtual>> SIGNI on your center SIGNI zone gets +3000 power.\n" +
                "@U: When this SIGNI is banished, until end of turn, all of your <<Virtual>> SIGNI get +3000 power." +
                "~#Target up to 2 <<Virtual>> SIGNI from your trash, and add them to your hand."
        );
        
		setName("zh_simplified", "2434代号 樋口枫");
        setDescription("zh_simplified", 
                "@C :你的中央的精灵区的<<バーチャル>>精灵的力量+3000。\n" +
                "@U :当这只精灵被破坏时，直到回合结束时为止，你的全部的<<バーチャル>>精灵的力量+3000。" +
                "~#从你的废弃区把<<バーチャル>>精灵2张最多作为对象，将这些加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
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
            
            registerConstantAbility(this::onConstEffSharedCond, new TargetFilter().own().SIGNI(), new PowerModifier(3000));
            
            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffSharedCond(CardIndex cardIndex)
        {
            return cardIndex.getLocation() == CardLocation.SIGNI_CENTER &&
                   cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.VIRTUAL) ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onAutoEff()
        {
            gainPower(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).getExportedData(), 3000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromTrash());
            addToHand(data);
        }
    }
}
